ALTER TABLE LICENSES_PRICING
    ALTER COLUMN CREATED_AT DROP NOT NULL;
ALTER TABLE LICENSES_PRICING
    ALTER COLUMN CREATED_AT SET DEFAULT NOW();
ALTER TABLE LICENSES_PRICING
    ALTER COLUMN UPDATED_AT DROP NOT NULL;
ALTER TABLE LICENSES_PRICING
    ALTER COLUMN UPDATED_AT SET DEFAULT NOW();

ALTER TABLE LICENSES
    ALTER COLUMN CREATED_AT DROP NOT NULL;
ALTER TABLE LICENSES
    ALTER COLUMN CREATED_AT SET DEFAULT NOW();
ALTER TABLE LICENSES
    ALTER COLUMN UPDATED_AT DROP NOT NULL;
ALTER TABLE LICENSES
    ALTER COLUMN UPDATED_AT SET DEFAULT NOW();

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name)
VALUES (2, 'ROLE_CLIENT');

INSERT INTO member_available_status(id, description)
values (1, 'Available'),
       (2, 'Partially available'),
       (3, 'Busy');

INSERT INTO license_pay_cycle (id, description)
VALUES (1, 'Unique'),
       (2, 'Monthly'),
       (3, 'Annually'),
       (4, 'Relative');

INSERT INTO license_type(id, description)
VALUES (1, 'Open Source');
INSERT INTO license_type(id, description)
VALUES (2, 'Premium');
INSERT INTO license_type(id, description)
VALUES (3, 'Self-hosted');

INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (1, 'Planning', NOW(), NOW());
INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (2, 'Build', NOW(), NOW());
INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (3, 'Testing', NOW(), NOW());
INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (4, 'CI/CD', NOW(), NOW());
INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (5, 'Configuration', NOW(), NOW());
INSERT INTO devops_categories (id, description, created_at, updated_at)
VALUES (6, 'Monitoring', NOW(), NOW());

INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (1, 'Kanban Board', 1, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (2, 'Issue Tracking', 1, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (3, 'Java', 2, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (4, '.NET', 2, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (5, 'Automatized test', 3, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (6, 'Performance test', 3, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (7, 'Repositories', 4, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (8, 'Continuous integration', 4, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (9, 'Continuous deployment', 4, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (10, 'Servers', 5, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (11, 'Servers', 6, NOW(), NOW());
INSERT INTO devops_subcategories (devops_subcategory_id, description, devops_category_id, created_at, updated_at)
VALUES (12, 'Logging', 6, NOW(), NOW());

INSERT INTO TOOLS(tool_id, image_url, name, created_at, updated_at)
VALUES (1, '/plan/azure_devops.png', 'Azure DevOps', now(), now()),
       (2, '/plan/gitlab.png', 'Gitlab', now(), now()),
       (3, '/plan/jira.png', 'Jira', now(), now()),
       (4, '/plan/trello.png', 'Trello', now(), now()),
       (5, '/build/gradle.png', 'Gradle', now(), now()),
       (6, '/build/maven.png', 'Maven', now(), now()),
       (7, '/build/msbuild.png', 'MSBuild', now(), now()),
       (8, '/build/nant.png', 'Nant', now(), now()),
       (9, '/test/selenium.png', 'Selenium', now(), now()),
       (10, '/test/katalon.png', 'Katalon', now(), now()),
       (11, '/test/jmeter.svg', 'Jmeter', now(), now()),
       (12, '/test/load_runner.png', 'Load runner', now(), now()),
       (13, '/cicd/github.png', 'Github', now(), now()),
       (14, '/cicd/jenkins.png', 'Jenkins', now(), now()),
       (15, '/configure/chef.png', 'Chef', now(), now()),
       (16, '/configure/puppet.png', 'Puppet', now(), now()),
       (17, '/configure/ansible.png', 'Ansible', now(), now()),
       (18, '/monitor/nagios.png', 'Nagios', now(), now()),
       (19, '/monitor/datadog.png', 'Datadog', now(), now())
;
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (1, 1);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (4, 1);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (3, 1);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (1, 2);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (2, 2);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (3, 2);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (5, 3);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (6, 3);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (7, 4);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (8, 4);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (9, 5);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (10, 5);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (11, 6);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (12, 6);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (1, 7);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (13, 7);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (2, 7);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (1, 8);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (13, 8);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (2, 8);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (14, 8);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (1, 9);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (13, 9);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (2, 9);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (14, 9);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (15, 10);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (16, 10);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (17, 10);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (18, 11);
INSERT INTO tool_subcategory (tool_id, subcategory_id)
VALUES (19, 12);

INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (2, 'Basic and test plan', 'test', 2, 2, 1);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (3, 'Azure Devops Server', 'test', 2, 2, 1);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (4, 'Bronze', 'test', 2, 2, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (5, 'Silver', 'test', 2, 2, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (6, 'Gold', 'test', 2, 2, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (7, 'Starter', 'test', 2, 3, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (8, 'Premium', 'test', 2, 3, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (9, 'Ultimate', 'test', 2, 3, 2);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (10, 'STANDARD', 'test', 2, 2, 3);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (11, 'PREMIUM', 'test', 2, 2, 3);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (12, 'Server', 'test', 3, 3, 3);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (13, 'Data Center', 'test', 3, 3, 3);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (14, 'Business Class', 'test', 2, 2, 4);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (15, 'Enterprise', 'test', 2, 2, 4);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (16, 'Gradle', 'test', 3, 1, 5);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (17, 'Maven', 'test', 3, 1, 6);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (18, 'MSBuild', 'test', 3, 1, 7);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (19, 'Nant', 'test', 3, 1, 8);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (20, 'Selenium', 'test', 3, 1, 9);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (21, 'Katalon Studio', 'test', 3, 1, 10);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (22, 'Jmeter', 'test', 3, 1, 11);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (23, 'LoadRunner', 'test', 3, 2, 12);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (24, 'Team', 'test', 2, 2, 13);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (25, 'Enterprise', 'test', 2, 2, 13);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (26, 'Jenkins', 'test', 3, 1, 14);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (27, 'Chef', 'test', 3, 1, 15);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (28, 'Free', 'test', 3, 1, 16);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (29, 'Enterprise', 'test', 2, 2, 16);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (30, 'STANDARD', 'test', 2, 2, 17);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (31, 'PREMIUM', 'test', 2, 2, 17);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (32, 'Standard', 'test', 3, 2, 18);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (33, 'Enterprise', 'test', 3, 2, 18);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (34, 'Infrastructure', 'test', 2, 2, 19);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (35, 'APM', 'test', 2, 2, 19);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (36, 'Log Management', 'test', 2, 2, 19);
INSERT INTO licenses (license_id, name, observation, pay_cycle_id, license_type_id, tool_id)
VALUES (1, 'Basic plan', 'test', 2, 2, 1);

INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (7, -1, 1, 99, 6);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (8, -1, 1, 4, 7);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (9, -1, 1, 19, 8);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (10, -1, 1, 99, 9);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (11, 100, 1, 7, 10);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (12, 250, 101, 6, 10);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (13, 5000, 251, 5, 10);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (14, 100, 1, 14, 11);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (15, 250, 101, 10, 11);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (16, 5000, 251, 7, 11);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (17, 10, 1, 10, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (18, 25, 1, 3500, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (19, 50, 1, 6800, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (20, 100, 1, 13300, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (21, 250, 1, 26400, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (22, 500, 1, 40000, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (23, 2000, 1, 60000, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (24, 10000, 1, 120000, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (25, -1, 1, 200000, 12);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (26, 500, 1, 20400, 13);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (27, 1000, 1, 30000, 13);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (28, 2000, 1, 52800, 13);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (29, 3000, 1, 79200, 13);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (30, 10000, 1, 264000, 13);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (31, 100, 1, 9.99, 14);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (32, 20, 1, 20.83, 15);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (33, 500, 1, 17.5, 15);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (34, 1000, 1, 12.92, 15);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (35, 10000, 1, 4.83, 15);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (36, -1, 1, 0, 16);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (37, -1, 1, 0, 17);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (38, -1, 1, 0, 18);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (39, -1, 1, 0, 19);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (40, -1, 1, 0, 20);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (41, -1, 1, 0, 21);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (42, -1, 1, 0, 22);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (43, -1, 1, -1, 23);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (44, 5, 1, 25, 24);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (45, -1, 1, 9, 24);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (46, -1, 1, -1, 25);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (47, -1, 1, 0, 26);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (48, -1, 1, 0, 27);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (49, 10, 1, 0, 28);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (50, -1, 1, 0, 29);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (51, -1, 1, -1, 30);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (52, -1, 1, -1, 31);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (53, -1, 1, 1995, 32);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (54, -1, 1, 3495, 33);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (55, 1, 1, 18, 34);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (56, -1, 1, 36, 35);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (57, -1, 1, 1.91, 36);
INSERT INTO licenses_pricing (license_pricing_id, max_users, min_users, price, license_id)
VALUES (1, 5, 1, 0, 1);


INSERT INTO level (level_id, description)
VALUES (1, 'Senior');
INSERT INTO level (level_id, description)
VALUES (2, 'Semi senior');
INSERT INTO level (level_id, description)
VALUES (3, 'Junior');

INSERT INTO profile (id, description)
VALUES (1, 'Backend Developer');
INSERT INTO profile (id, description)
VALUES (2, 'Quality Assurance');
INSERT INTO profile (id, description)
VALUES (3, 'Frontend Developer');
INSERT INTO profile (id, description)
VALUES (4, 'DevOps Engineer');