-- INSERT INTO TBL_USER (created_by, created_date, email, password, phone, role, updated_by, updated_date, username, status)
-- VALUES (0, now(), 'phovodon@gmail.com', '$2a$10$VpMmVWO5u/0K9fku2PVaZeB0aANg17TQ9xB6fqYep87iSLi6VCWce', '0974059394', 'Admin', 0, now(), 'Admin', 1);

INSERT INTO tbl_user (created_by, created_date, email, password, phone, role, updated_by, updated_date, username, status)
VALUES (0, SYSDATE, 'phovodon@gmail.com', '$2a$10$VpMmVWO5u/0K9fku2PVaZeB0aANg17TQ9xB6fqYep87iSLi6VCWce', '0974059394', 'Admin', 0, SYSDATE, 'Admin', 1);