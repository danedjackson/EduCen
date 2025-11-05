// java
package com.jackson.educen.services.impl;

import com.jackson.educen.documents.UserDocument;
import com.jackson.educen.mappers.IFileMapper;
import com.jackson.educen.mappers.IUserMapper;
import com.jackson.educen.models.ApiResponse;
import com.jackson.educen.documents.FileDocument;
import com.jackson.educen.models.Role;
import com.jackson.educen.models.dto.File;
import com.jackson.educen.models.dto.FileDownload;
import com.jackson.educen.models.dto.User.User;
import com.jackson.educen.models.dto.User.UserDTO;
import com.jackson.educen.models.dto.User.UserFile;
import com.jackson.educen.models.requests.UpdatePlanRequest;
import com.jackson.educen.repositories.IFileRepository;
import com.jackson.educen.repositories.IUserRepository;
import com.jackson.educen.services.ILogger;
import com.jackson.educen.services.ITeacherService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import com.jackson.educen.utils.Util;

@Service
public class TeacherService implements ITeacherService {

    private final IFileRepository documentRepository;
    private final IUserRepository userRepository;
    private final UserService userService;
    private final IUserMapper userMapper;
    private final IFileMapper fileMapper;
    private final ILogger logger;

    public TeacherService(IFileRepository documentRepository, IUserRepository userRepository, UserService userService, IUserMapper userMapper, IFileMapper fileMapper, ILogger logger) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
        this.logger = logger;
    }
    @Override
    public ApiResponse<FileDocument> uploadFile(MultipartFile file, String subject) {
        // 1️⃣ Extract teacher ID from the uploaded file's name
        String teacherId = extractTeacherId(file);
        if (teacherId == null) {
            return Util.failure(HttpStatus.NOT_FOUND, "Could not determine file name.");
        }

        // 2️⃣ Fetch teacher information from the database
        UserDocument teacher = userRepository.findById(cleanFileName(teacherId))
                .orElse(null);
        if (teacher == null) {
            return Util.failure(HttpStatus.NOT_FOUND, "Could not find teacher information.");
        }

        // 3️⃣ Validate and determine the file's content type
        String contentType = resolveContentType(file);
        if (contentType == null) {
            return Util.failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Only PDF (.pdf) and Word (.doc, .docx) files are accepted.");
        }

        try {
            // 4️⃣ Save the file to disk and store its metadata in the database
            FileDocument savedFile = storeFile(file, teacher, subject, contentType);

            logger.infoLog("Stored document for teacher ID: " + teacher.getId() +
                    " at path: " + savedFile.getFilePath());

            // 5️⃣ Return a success response
            return Util.success(savedFile,
                    "Successfully stored document to server and saved metadata to database");

        } catch (IOException e) {
            logger.errorLog("Failed saving file to disk: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving file to server storage.");
        } catch (Exception e) {
            logger.errorLog("Failed saving file metadata: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving file record to database.");
        }
    }

    @Override
    public ApiResponse<FileDownload> getLessonPlan(String id) {
        Optional<FileDocument> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            logger.errorLog("Could not retrieve document with ID: " + id);
            return Util.failure(HttpStatus.NOT_FOUND, "Unable to retrieve document");
        }

        FileDocument fileDocument = document.get();
        String filePath = fileDocument.getFilePath();
        if (filePath == null || filePath.isBlank()) {
            logger.errorLog("File path missing for document ID: " + id);
            return Util.failure(HttpStatus.NOT_FOUND, "File not found on server");
        }

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            logger.errorLog("File not found on disk for document ID: " + id + " path: " + filePath);
            return Util.failure(HttpStatus.NOT_FOUND, "File not found on server");
        }

        try {
            byte[] content = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            FileDownload download = new FileDownload(content, fileDocument.getContentType(), fileName);
            return Util.success(download, "Retrieved document");
        } catch (IOException e) {
            logger.errorLog("Failed to read file from disk for document ID: " + id + ": " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file from server");
        }
    }
    @Override
    public ApiResponse<List<FileDocument>> getLessonPlans(String id) {
        Optional<FileDocument> documents = documentRepository.findById(id);
        if(documents.isEmpty()) {
            logger.errorLog("Could not retrieve document with ID: " + id);
            return Util.failure(HttpStatus.NOT_FOUND, "Unable to retrieve document");
        }
        List<FileDocument> lessonPlans = documents
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());

        return Util.success(lessonPlans, "Retrieved document");
    }

    @Override
    public ApiResponse<List<FileDocument>> getTeacherLessonPlans(String id) {
        Optional<FileDocument> documents = documentRepository.findAllByTeacherId(id);
        if(documents.isEmpty()){
            return Util.failure(HttpStatus.NOT_FOUND, "No lesson plans found for the given teacher ID.");
        }

        List<FileDocument> lessonPlans = documents
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        return Util.success(lessonPlans, "Retrieved document");
    }

    /*
    So in summary, this takes no inputs, retrieves uploaded documents and their teacher IDs,
    finds the teacher user data for those IDs, transforms the user data to simplified User objects,
    and returns the list of teachers who have uploaded lesson plan documents. The main logic is
    around aggregating and transforming the data from documents to teacher users.
     */
    @Override
    public ApiResponse<List<UserFile>> getAllTeacherPlans() {
        List<FileDocument> documents = documentRepository.findAll();

        HashSet<String> teacherIds = new HashSet<>();
        documents.forEach(document -> teacherIds.add(document.getTeacherId()));

        if(teacherIds.isEmpty()){
            return Util.failure(HttpStatus.NOT_FOUND, "No documents with teacher IDs found");
        }
        // Find all teachers who have uploaded documents
        List<UserDocument> teachersWithDocuments = userRepository.findAllById(teacherIds);
        if(teachersWithDocuments.isEmpty()){
            return Util.failure(HttpStatus.NOT_FOUND, "No documents found");
        }
        List<UserFile> teachers = new ArrayList<>();

        // Transforming raw user data
        teachersWithDocuments.forEach(document -> {
            teachers.add(userMapper.userDocumentToUserFile(document));
        });

        // Populate files for each teacher
        for(UserFile teacher : teachers) {
            List<File> files = new ArrayList<>();

            for (FileDocument doc : documents) {
                if (doc.getTeacherId().equals(teacher.getId())) {
                    File file = fileMapper.fileDocumentToFile(doc);
                    files.add(file);
                }
            }
            teacher.setLessonPlans(files);
        }

        if(teachers.isEmpty()){
            return Util.failure(HttpStatus.NOT_FOUND, "No teacher information found");
        }
        return Util.success(teachers, "Successfully retrieved teachers with uploaded lesson plans");
    }

    @Override
    public ApiResponse<String> updateFile(FileDocument fileInfo) {
        Optional<FileDocument> document = documentRepository.findById(cleanFileName(fileInfo.getId()));
        if(document.isEmpty()){
            return Util.failure(HttpStatus.NOT_FOUND, "Could not find file information.");
        }
        FileDocument fileDocument = document.get();
        fileDocument.setComments(fileInfo.getComments());
        fileDocument.setChecked(true);
        fileDocument.setDateModified(LocalDateTime.now());

        documentRepository.save(fileDocument);

        return Util.success(fileDocument.getId(), "Updated record for document");
    }

    @Override
    public ApiResponse<List<FileDocument>> updateFileComments(UpdatePlanRequest planComments) {
        try {
            List<FileDocument> plansToSave = new ArrayList<>();

            // Iterate over the keys (plan IDs) in the map
            for (String planId : planComments.getPlanComments().keySet()) {
                String comments = planComments.getPlanComments().get(planId);

                // Use planId and comments as needed
                Optional<FileDocument> document = documentRepository.findById(cleanFileName(planId));
                if (document.isPresent()) {
                    FileDocument currentDoc = document.get();
                    currentDoc.setComments(comments);
                    currentDoc.setChecked(true);
                    plansToSave.add(currentDoc);
                }
            }

            documentRepository.saveAll(plansToSave);

            return Util.success(plansToSave, "Updated record for document");
        } catch(Exception e){
            logger.errorLog("An error occurred updating comments for lesson plans: " + e.getMessage());
            return Util.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update lesson plan comments");
        }
    }
    @Override
    public ApiResponse<User> getStudentById(String id) {
        return userService.getUserById(id);
    }

    @Override
    public ApiResponse<List<User>> getAllStudents() {
        return userService.getAllUsersByRole(Role.STUDENT.name());
    }

    @Override
    public ApiResponse<User> addNewStudent(UserDTO user) {
        user.setRole(Role.STUDENT);
        return userService.addNewUser(user);
    }

    @Override
    public ApiResponse<User> editStudentDetails(UserDTO user) {
        return userService.editUserDetails(user);
    }


    private String cleanFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName;
        }
    }

    // Extract the teacher ID from the file name (used as identifier)
    private String extractTeacherId(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null || name.isBlank()) {
            logger.infoLog("Invalid file name for file provided");
            return null;
        }
        return name;
    }

    // Validate file type by MIME type or extension (only allow PDF and Word docs)
    private String resolveContentType(MultipartFile file) {
        Set<String> allowedTypes = Set.of(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );

        String contentType = file.getContentType();
        String lowerName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase(Locale.ROOT);

        boolean mimeAllowed = contentType != null && allowedTypes.contains(contentType);
        boolean extAllowed = lowerName.endsWith(".pdf") || lowerName.endsWith(".doc") || lowerName.endsWith(".docx");

        if (!mimeAllowed && !extAllowed) {
            logger.infoLog("Rejected file due to invalid type: " + contentType + " / " + lowerName);
            return null;
        }

        // Infer type from extension if MIME type is missing or unrecognized
        if (contentType == null || !allowedTypes.contains(contentType)) {
            if (lowerName.endsWith(".pdf")) return "application/pdf";
            if (lowerName.endsWith(".doc")) return "application/msword";
            if (lowerName.endsWith(".docx"))
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }

        return contentType;
    }

    // Save the file to disk and create a FileDocument entry in the database
    private FileDocument storeFile(MultipartFile file, UserDocument teacher,
                                   String subject, String contentType) throws IOException {
        FileDocument doc = new FileDocument();

        // Set basic file metadata
        doc.setDateUploaded(LocalDate.now());
        doc.setTeacherId(teacher.getId());
        doc.setSubject(subject != null && !subject.isBlank() ? subject.trim() : null);
        doc.setContentType(contentType);

        // Build a clean, readable title for the file
        String title = buildTitle(doc, teacher);
        doc.setTitle(title);

        // Create the teacher’s folder if it doesn’t exist
        Path baseDir = Paths.get(System.getProperty("user.dir"), "lesson_plans", teacher.getId());
        Files.createDirectories(baseDir);

        // Generate a unique file path (add suffix if needed)
        Path target = buildUniqueFilePath(baseDir, title, file.getOriginalFilename());

        // Copy uploaded file to the target location
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Save the full file path to the database
        doc.setFilePath(target.toAbsolutePath().toString());
        return documentRepository.save(doc);
    }

    // Build a readable file title based on teacher info and subject
    private String buildTitle(FileDocument doc, UserDocument teacher) {
        String base = String.join("_",
                doc.getDateUploaded().toString(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getGrade()
        );
        if (doc.getSubject() != null) {
            base += "_" + doc.getSubject().replaceAll("\\s+", "_");
        }
        return base;
    }

    // Create a unique filename — add numeric suffix if a file already exists
    private Path buildUniqueFilePath(Path baseDir, String baseTitle, String originalName) throws IOException {
        String safeBase = baseTitle.replaceAll("[^a-zA-Z0-9_\\-.]", "_");
        String candidate = safeBase + "_" + Paths.get(originalName).getFileName();
        Path target = baseDir.resolve(candidate);

        if (!Files.exists(target)) return target;

        // Find the next available numeric suffix
        int suffix = nextSuffix(baseDir, safeBase);
        return baseDir.resolve(safeBase + "-" + suffix + "_" + originalName);
    }

    // Find the next numeric suffix for duplicate filenames
    private int nextSuffix(Path dir, String baseName) throws IOException {
        int max = 0;
        try (Stream<Path> stream = Files.list(dir)) {
            for (Path p : (Iterable<Path>) stream::iterator) {
                String fname = p.getFileName().toString();
                if (!fname.startsWith(baseName + "_") && !fname.startsWith(baseName + "-")) continue;

                Matcher m = Pattern.compile(baseName + "-(\\d+)_").matcher(fname);
                if (m.find()) {
                    max = Math.max(max, Integer.parseInt(m.group(1)));
                } else {
                    max = Math.max(max, 1);
                }
            }
        }
        return max + 1;
    }


}
