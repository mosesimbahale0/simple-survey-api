CREATE DATABASE IF NOT EXISTS sky_survey_db;
USE sky_survey_db;

-- ============================
-- SURVEYS
-- ============================
CREATE TABLE IF NOT EXISTS surveys (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       description TEXT,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================
-- QUESTIONS
-- ============================
CREATE TABLE IF NOT EXISTS questions (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         survey_id BIGINT NOT NULL,
                                         name VARCHAR(255) NOT NULL,
                                         type ENUM('short_text', 'long_text', 'email', 'choice', 'file') NOT NULL,
                                         required VARCHAR(10) NOT NULL DEFAULT 'no',
                                         text TEXT,
                                         description TEXT,

    -- choice-question fields
                                         options_multiple VARCHAR(10),

    -- file-question fields
                                         file_format VARCHAR(20),
                                         max_file_size INT,
                                         max_file_size_unit VARCHAR(10),
                                         file_multiple VARCHAR(10),

                                         display_order INT DEFAULT 0,

                                         FOREIGN KEY (survey_id) REFERENCES surveys(id) ON DELETE CASCADE
);

-- ============================
-- QUESTION OPTIONS (for choice questions)
-- ============================
CREATE TABLE IF NOT EXISTS question_options (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                question_id BIGINT NOT NULL,
                                                value VARCHAR(255) NOT NULL,
                                                label VARCHAR(255) NOT NULL,
                                                display_order INT DEFAULT 0,
                                                FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

-- ============================
-- SURVEY RESPONSES (one per submission)
-- ============================
CREATE TABLE IF NOT EXISTS survey_responses (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                survey_id BIGINT NOT NULL,
                                                full_name VARCHAR(255),
                                                email_address VARCHAR(255) NOT NULL,
                                                submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                FOREIGN KEY (survey_id) REFERENCES surveys(id) ON DELETE CASCADE,
                                                INDEX idx_survey_email (survey_id, email_address)
);

-- ============================
-- ANSWERS (one per question per response)
-- ============================
CREATE TABLE IF NOT EXISTS answers (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       response_id BIGINT NOT NULL,
                                       question_id BIGINT NOT NULL,
                                       answer_text TEXT,
                                       FOREIGN KEY (response_id) REFERENCES survey_responses(id) ON DELETE CASCADE,
                                       FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

-- ============================
-- CERTIFICATES (file uploads, supports multiple per response)
-- ============================
CREATE TABLE IF NOT EXISTS certificates (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            response_id BIGINT NOT NULL,
                                            question_id BIGINT NOT NULL,
                                            original_filename VARCHAR(255) NOT NULL,
                                            stored_filename VARCHAR(255) NOT NULL,
                                            file_path VARCHAR(500) NOT NULL,
                                            content_type VARCHAR(100),
                                            file_size BIGINT,
                                            uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (response_id) REFERENCES survey_responses(id) ON DELETE CASCADE,
                                            FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);