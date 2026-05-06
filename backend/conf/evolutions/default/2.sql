# --- !Ups

INSERT INTO `user` (id, user_name, first_name, last_name, user_type, rating, rating_count, recommend_rating, recommend_rating_count, service_provider, service_execution_counts, service_user, unread_mention) VALUES
(8001, 'jsmith_cs', 'John', 'Smith', 1, 0, 0, 0, 0, false, 0, false, false),
(8002, 'jdoe_cs', 'Jane', 'Doe', 1, 0, 0, 0, 0, false, 0, false, false),
(8003, 'bjohnson_cs', 'Bob', 'Johnson', 1, 0, 0, 0, 0, false, 0, false, false),
(8004, 'abrown_cs', 'Alice', 'Brown', 1, 0, 0, 0, 0, false, 0, false, false),
(8005, 'cwilson_ece', 'Carol', 'Wilson', 1, 0, 0, 0, 0, false, 0, false, false),
(8006, 'dtaylor_ece', 'David', 'Taylor', 1, 0, 0, 0, 0, false, 0, false, false),
(8007, 'emartinez_ece', 'Eva', 'Martinez', 1, 0, 0, 0, 0, false, 0, false, false),
(8008, 'fanderson_me', 'Frank', 'Anderson', 1, 0, 0, 0, 0, false, 0, false, false),
(8009, 'gthomas_me', 'Grace', 'Thomas', 1, 0, 0, 0, 0, false, 0, false, false),
(8010, 'hlee_me', 'Henry', 'Lee', 1, 0, 0, 0, 0, false, 0, false, false);

INSERT INTO researcher_info (user_id, department, school, research_fields) VALUES
(8001, 'Computer Science', 'Lyle School of Engineering', 'Machine Learning'),
(8002, 'Computer Science', 'Lyle School of Engineering', 'Cybersecurity'),
(8003, 'Computer Science', 'Lyle School of Engineering', 'Data Science'),
(8004, 'Computer Science', 'Lyle School of Engineering', 'NLP'),
(8005, 'Electrical Engineering', 'Lyle School of Engineering', 'Signal Processing'),
(8006, 'Electrical Engineering', 'Lyle School of Engineering', 'Robotics'),
(8007, 'Electrical Engineering', 'Lyle School of Engineering', 'Computer Vision'),
(8008, 'Mechanical Engineering', 'Lyle School of Engineering', 'Thermodynamics'),
(8009, 'Mechanical Engineering', 'Lyle School of Engineering', 'Fluid Dynamics'),
(8010, 'Mechanical Engineering', 'Lyle School of Engineering', 'Materials Science');

# --- !Downs

DELETE FROM researcher_info WHERE user_id IN (8001,8002,8003,8004,8005,8006,8007,8008,8009,8010);
DELETE FROM `user` WHERE id IN (8001,8002,8003,8004,8005,8006,8007,8008,8009,8010);
