INSERT INTO CLASSIFICATION VALUES ('1', '', 'EXTERN', 'BRIEF','nova-domain', TRUE, CURRENT_TIMESTAMP, 'ROOT', 'DESC', 1, 'P1D', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, '9999-12-31');
INSERT INTO CLASSIFICATION VALUES ('2', '1','MANUELL', 'BRIEF', 'nova-domain', TRUE, CURRENT_TIMESTAMP, 'CHILD', 'DESC', 1, 'P1D', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, '9999-12-31');
INSERT INTO CLASSIFICATION VALUES ('3', '1','MASCHINELL', 'BRIEF', '', FALSE, CURRENT_TIMESTAMP, 'ANOTHER CHILD', 'DESC', 1, 'P2D', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, '9999-12-31');
INSERT INTO CLASSIFICATION VALUES ('4', '2','PROZESS', 'EXCEL-SHEET', '', TRUE, CURRENT_TIMESTAMP, 'GRANDCHILD', 'DESC', 1, 'P1DT4H12S', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, '9999-12-31');

INSERT INTO CLASSIFICATION VALUES('13', '3', 'MANUELL', '', '', TRUE, CURRENT_TIMESTAMP, 'ANOTHER GRANDCHILD', 'DESC', 3, 'P3DT12H', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLASSIFICATION VALUES('14', '4', 'MANUELL', '', '', FALSE, CURRENT_TIMESTAMP, 'BIG GRANDCHILD', 'DESC', 2, 'P2DT12H', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLASSIFICATION VALUES('15', '3', 'PROZESS', 'MINDMAP', 'nova-domain', FALSE, CURRENT_TIMESTAMP, 'SMALL GRANDCHILD', 'DESC', 4, 'P5DT12H', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO CLASSIFICATION VALUES('16', '4', 'MANUELL', '', 'nova-domain', TRUE, CURRENT_TIMESTAMP, 'NO GRANDCHILD', 'DESC', 3, 'P3DT', 'custom 1', 'custom 2', 'custom 3', 'custom 4', 'custom 5', 'custom 6', 'custom 7', 'custom 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
