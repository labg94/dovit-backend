ALTER TABLE LICENSES_PRICING ALTER COLUMN CREATED_AT DROP NOT NULL;
ALTER TABLE LICENSES_PRICING ALTER COLUMN CREATED_AT SET DEFAULT NOW();
ALTER TABLE LICENSES_PRICING ALTER COLUMN UPDATED_AT DROP NOT NULL;
ALTER TABLE LICENSES_PRICING ALTER COLUMN UPDATED_AT SET DEFAULT NOW();

ALTER TABLE LICENSES ALTER COLUMN CREATED_AT DROP NOT NULL;
ALTER TABLE LICENSES ALTER COLUMN CREATED_AT SET DEFAULT NOW();
ALTER TABLE LICENSES ALTER COLUMN UPDATED_AT DROP NOT NULL;
ALTER TABLE LICENSES ALTER COLUMN UPDATED_AT SET DEFAULT NOW();

INSERT INTO dovit.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO dovit.roles (id, name) VALUES (2, 'ROLE_CLIENT');

INSERT INTO dovit.license_type(id, description) VALUES(1, 'Open Source');
INSERT INTO dovit.license_type(id, description) VALUES(2, 'Premium');
INSERT INTO dovit.license_type(id, description) VALUES(3, 'Self-hosted');

INSERT INTO dovit.devops_categories (id, description) VALUES (1, 'Planificación');
INSERT INTO dovit.devops_categories (id, description) VALUES (2, 'Build');
INSERT INTO dovit.devops_categories (id, description) VALUES (3, 'Testing');
INSERT INTO dovit.devops_categories (id, description) VALUES (4, 'CI/CD');
INSERT INTO dovit.devops_categories (id, description) VALUES (5, 'Configuración');
INSERT INTO dovit.devops_categories (id, description) VALUES (6, 'Monitoreo');

INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (1, 'Tablero Kanban', 1);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (2, 'Issue Tracking', 1);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (3, 'Java', 2);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (4, '.NET', 2);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (5, 'Pruebas automatizadas', 3);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (6, 'Pruebas de performance', 3);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (7, 'Repositorios', 4);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (8, 'Integración continua', 4);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (9, 'Despliegue continuo', 4);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (10, 'Servidores', 5);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (11, 'Servidores', 6);
INSERT INTO dovit.devops_subcategories (devops_subcategory_id, description, devops_category_id) VALUES (12, 'Logging', 6);

INSERT INTO TOOLS(tool_id, image_url, name) VALUES
(1,'/plan/azure_devops.png','Azure DevOps'),
(2,'/plan/gitlab.png','Gitlab'),
(3,'/plan/jira.png','Jira'),
(4,'/plan/trello.png','Trello'),
(5,'/build/gradle.png','Gradle'),
(6,'/build/maven.png','Maven'),
(7,'/build/msbuild.png','MSBuild'),
(8,'/build/nant.png','Nant'),
(9,'/test/selenium.png','Selenium'),
(10,'/test/katalon.png','Katalon'),
(11,'/test/jmeter.svg','Jmeter'),
(12,'/test/load_runner.png','Load runner'),
(13,'/cicd/github.png','Github'),
(14,'/cicd/jenkins.png','Jenkins'),
(15,'/configure/chef.png','Chef'),
(16,'/configure/puppet.png','Puppet'),
(17,'/configure/ansible.png','Ansible'),
(18,'/monitor/nagios.png','Nagios'),
(19,'/monitor/datadog.png','Datadog')
;
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (1,1);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (4,1);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (3,1);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (1,2);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (2,2);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (3,2);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (5,3);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (6,3);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (7,4);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (8,4);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (9,5);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (10,5);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (11,6);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (12,6);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (1,7);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (13,7);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (2,7);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (1,8);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (13,8);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (2,8);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (14,8);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (1,9);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (13,9);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (2,9);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (14,9);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (15,10);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (16,10);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (17,10);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (18,11);
INSERT INTO dovit.tool_subcategory (tool_id, subcategory_id) VALUES (19,12);

INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (2, 'Basica y test plan', 'test', 'mensual', 2, 1);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (3, 'Azure Devops Server', 'test', 'mensual', 2, 1);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (4, 'Bronze', 'test', 'mensual', 2, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (5, 'Silver', 'test', 'mensual', 2, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (6, 'Gold', 'test', 'mensual', 2, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (7, 'Starter', 'test', 'mensual', 3, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (8, 'Premium', 'test', 'mensual', 3, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (9, 'Ultimate', 'test', 'mensual', 3, 2);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (10, 'STANDARD', 'test', 'mensual', 2, 3);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (11, 'PREMIUM', 'test', 'mensual', 2, 3);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (12, 'Server', 'test', 'unico', 3, 3);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (13, 'Data Center', 'test', 'anual', 3, 3);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (14, 'Business Class', 'test', 'mensual', 2, 4);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (15, 'Enterprise', 'test', 'mensual', 2, 4);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (16, 'Gradle', 'test', 'unico', 1, 5);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (17, 'Maven', 'test', 'unico', 1, 6);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (18, 'MSBuild', 'test', 'unico', 1, 7);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (19, 'Nant', 'test', 'unico', 1, 8);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (20, 'Selenium', 'test', 'unico', 1, 9);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (21, 'Katalon Studio', 'test', 'unico', 1, 10);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (22, 'Jmeter', 'test', 'unico', 1, 11);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (23, 'LoadRunner', 'test', 'unico', 2, 12);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (24, 'Team', 'test', 'mensual', 2, 13);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (25, 'Enterprise', 'test', 'mensual', 2, 13);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (26, 'Jenkins', 'test', 'unico', 1, 14);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (27, 'Chef', 'test', 'unico', 1, 15);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (28, 'Free', 'test', 'unico', 1, 16);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (29, 'Enterprise', 'test', 'mensual', 2, 16);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (30, 'STANDARD', 'test', 'mensual', 2, 17);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (31, 'PREMIUM', 'test', 'mensual', 2, 17);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (32, 'Standard', 'test', 'anual', 2, 18);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (33, 'Enterprise', 'test', 'anual', 2, 18);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (34, 'Infraestructure', 'test', 'mensual', 2, 19);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (35, 'APM', 'test', 'mensual', 2, 19);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (36, 'Log Management', 'test', 'mensual', 2, 19);
INSERT INTO dovit.licenses (license_id, name, observation, pay_cycle, license_type_id, tool_id) VALUES (1, 'Plan Basico', 'test', 'mensual', 2, 1);

INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (7, -1, 1, 99, 6);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (8, -1, 1, 4, 7);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (9, -1, 1, 19, 8);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (10, -1, 1, 99, 9);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (11, 100, 1, 7, 10);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (12, 250, 101, 6, 10);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (13, 5000, 251, 5, 10);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (14, 100, 1, 14, 11);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (15, 250, 101, 10, 11);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (16, 5000, 251, 7, 11);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (17, 10, 1, 10, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (18, 25, 1, 3500, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (19, 50, 1, 6800, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (20, 100, 1, 13300, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (21, 250, 1, 26400, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (22, 500, 1, 40000, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (23, 2000, 1, 60000, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (24, 10000, 1, 120000, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (25, -1, 1, 200000, 12);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (26, 500, 1, 20400, 13);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (27, 1000, 1, 30000, 13);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (28, 2000, 1, 52800, 13);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (29, 3000, 1, 79200, 13);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (30, 10000, 1, 264000, 13);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (31, 100, 1, 9.99, 14);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (32, 20, 1, 20.83, 15);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (33, 500, 1, 17.5, 15);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (34, 1000, 1, 12.92, 15);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (35, 10000, 1, 4.83, 15);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (36, -1, 1, 0, 16);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (37, -1, 1, 0, 17);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (38, -1, 1, 0, 18);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (39, -1, 1, 0, 19);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (40, -1, 1, 0, 20);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (41, -1, 1, 0, 21);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (42, -1, 1, 0, 22);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (43, -1, 1, -1, 23);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (44, 5, 1, 25, 24);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (45, -1, 1, 9, 24);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (46, -1, 1, -1, 25);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (47, -1, 1, 0, 26);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (48, -1, 1, 0, 27);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (49, 10, 1, 0, 28);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (50, -1, 1, 0, 29);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (51, -1, 1, -1, 30);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (52, -1, 1, -1, 31);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (53, -1, 1, 1995, 32);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (54, -1, 1, 3495, 33);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (55, 1, 1, 18, 34);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (56, -1, 1, 36, 35);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (57, -1, 1, 1.91, 36);
INSERT INTO dovit.licenses_pricing (license_pricing_id, max_users, min_users, price, license_id) VALUES (1, 5, 1, 0, 1);


INSERT INTO dovit.level (level_id, description) VALUES (1, 'Senior');
INSERT INTO dovit.level (level_id, description) VALUES (2, 'Semi senior');
INSERT INTO dovit.level (level_id, description) VALUES (3, 'Júnior');

INSERT INTO dovit.profile (id, description) VALUES (1, 'Backend Developer');
INSERT INTO dovit.profile (id, description) VALUES (2, 'Quality Assurance');
INSERT INTO dovit.profile (id, description) VALUES (3, 'Full Stack Developer');
INSERT INTO dovit.profile (id, description) VALUES (4, 'Frontend Developer');
INSERT INTO dovit.profile (id, description) VALUES (5, 'DevOps Engineer');