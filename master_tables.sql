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
