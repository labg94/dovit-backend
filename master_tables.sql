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

INSERT INTO roles (id, name, description)
VALUES (1, 'ROLE_ADMIN', 'Admnistrator');
INSERT INTO roles (id, name, description)
VALUES (2, 'ROLE_CLIENT', 'Client');
INSERT INTO roles (id, name, description)
VALUES (3, 'ROLE_CLIENT_ADMIN', 'Client administrator');

insert into users (id, created_at, updated_at, active, email, last_name, name, password, company_id, role_id, rut)
values (0, '2020-05-24 19:27:21.000000', '2020-05-24 19:27:24.000000', 'true', 'system@system.com', 'ADMINS', 'ADMINS',
        'admins', null, 1, 0);
INSERT INTO public.users (id, created_at, updated_at, active, email, last_name, name, password, rut, company_id, role_id) VALUES (-1, '2020-06-20 00:04:29.549000', '2020-07-02 12:00:16.486000', true, 'pariis78@gmail.com', 'París', 'Ramón', '$2a$10$ZB14LcTUwH5tKN5MgdWJCunOaEjBCmfgTbKAJtTz/umO.yS2byRci', '254126837-3', null, 1);


INSERT INTO member_available_status(id, description)
values (1, 'Available'),
       (2, 'Partially available'),
       (3, 'Busy');

INSERT INTO license_pay_cycle (id, description)
VALUES (1, 'Unique'),
       (2, 'Monthly'),
       (3, 'Annually'),
       (4, 'Relative');

INSERT INTO project_type (id, description, observation, created_at, updated_at)
VALUES (1, 'Web', 'A web project should have at least one frontend and one backend', now(), now()),
       (2, 'Microservices', 'A microservice project does not consider a frontend app', now(), now()),
       (3, 'Mobile', 'A mobile project consider at least one backend and a mobile app', now(), now()),
       (4, 'Desktop', 'A desktop project consider at least one desktop app and a backend', now(), now()),
       (5, 'Scrum methodology', 'Scrum methodology', now(), now());

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

INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (6, '2020-07-13 21:17:37.847255', '2020-07-13 23:37:31.234000', true, 'Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project''s build, reporting and documentation from a central piece of information.', '/build/maven.png', 'Maven');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (7, '2020-07-13 21:17:37.847255', '2020-07-13 23:38:23.803000', true, 'Microsoft product that helps automate the process of creating a software product, including compiling the source code, packaging, testing, deployment and creating documentations.', '/build/msbuild.png', 'MSBuild');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (17, '2020-07-13 21:17:37.847255', '2020-07-13 22:52:54.719000', true, 'Ansible is an open-source software provisioning, configuration management, and application-deployment tool enabling infrastructure as code. It runs on many Unix-like systems, and can configure both Unix-like systems as well as Microsoft Windows. It includes its own declarative language to describe system configuration', '/configure/ansible.png', 'Ansible');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (1, '2020-07-13 21:17:37.847255', '2020-07-13 22:54:51.406000', true, 'This is a Microsoft product that provides version control (either with Team Foundation Version Control (TFVC) or Git), reporting, requirements management, project management (for both agile software development and waterfall teams), automated builds, testing and release management capabilities. It covers the entire application lifecycle, and enables DevOps capabilities.', '/plan/azure_devops.png', 'Azure DevOps');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (19, '2020-07-13 21:17:37.847255', '2020-07-13 23:07:32.420000', true, 'Datadog is the essential monitoring platform for cloud applications. We bring together data from servers, containers, databases, and third-party services to make your stack entirely observable. These capabilities help DevOps teams avoid downtime, resolve performance issues, and ensure customers are getting the best user experience.', '/monitor/datadog.png', 'Datadog');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (5, '2020-07-13 21:17:37.847255', '2020-07-13 23:08:57.318000', true, 'Gradle is an open-source build automation tool that is designed to be flexible enough to build almost any type of software. ', '/build/gradle.png', 'Gradle');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (15, '2020-07-13 21:17:37.847255', '2020-07-13 23:09:25.545000', true, 'Chef is used to streamline the task of configuring and maintaining a company''s servers, and can integrate with cloud-based platforms such as Internap, Amazon EC2, Google Cloud Platform, Oracle Cloud, OpenStack, SoftLayer, Microsoft Azure, and Rackspace to automatically provision and configure new machines. Chef contains solutions for both small and large scale systems, with features and pricing for the respective ranges.', '/configure/chef.png', 'Chef');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (2, '2020-07-13 21:17:37.847255', '2020-07-13 23:11:44.038000', true, 'GitLab is a complete DevOps platform
With GitLab, you get a complete CI/CD toolchain in a single application. One interface. One conversation. One permission model. Thousands of features. You''ll be amazed at everything GitLab can do today. And we''re just getting started.', '/plan/gitlab.png', 'Gitlab');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (13, '2020-07-13 21:17:37.847255', '2020-07-13 23:16:57.644000', true, 'It offers the distributed version control and source code management (SCM) functionality of Git, plus its own features. It provides access control and several collaboration features such as bug tracking, feature requests, task management, and wikis for every project.', '/cicd/github.png', 'Github');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (14, '2020-07-13 21:17:37.847255', '2020-07-13 23:23:15.658000', true, 'It helps automate the parts of software development related to building, testing, and deploying, facilitating continuous integration and continuous delivery. It is a server-based system that runs in servlet containers such as Apache Tomcat. It supports version control tools, including AccuRev, CVS, Subversion, Git, Mercurial, Perforce, ClearCase and RTC, and can execute Apache Ant, Apache Maven and sbt based projects as well as arbitrary shell scripts and Windows batch commands.', '/cicd/jenkins.png', 'Jenkins');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (11, '2020-07-13 21:17:37.847255', '2020-07-13 23:27:39.019000', true, 'JMeter is tool that can be used as a load testing tool for analyzing and measuring the performance of a variety of services, with a focus on web applications. ', '/test/jmeter.svg', 'JMeter');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (18, '2020-07-13 21:17:37.847255', '2020-07-13 23:39:28.467000', true, 'Nagios is a powerful monitoring system that enables organizations to identify and resolve IT infrastructure problems before they affect critical business processes.', '/monitor/nagios.png', 'Nagios');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (8, '2020-07-13 21:17:37.847255', '2020-07-13 23:39:59.428000', true, 'NAnt is a free and open source software tool for automating software build processes. It is similar to Apache Ant, but targeted at the .NET environment rather than Java. The name NAnt comes from the fact that the tool is Not Ant', '/build/nant.png', 'Nant');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (3, '2020-07-13 21:17:37.847255', '2020-07-13 23:30:34.436000', true, 'Jira Software is built for every member of your software team to plan,
track, and release great software.', '/plan/jira.png', 'Jira');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (10, '2020-07-13 21:17:37.847255', '2020-07-13 23:31:57.291000', true, 'Katalon Studio is an automation testing solution developed by Katalon LLC. The software is built on top of the open-source automation frameworks Selenium, Appium with a specialized IDE interface for web, API, mobile and desktop application testing.', '/test/katalon.png', 'Katalon');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (12, '2020-07-13 21:17:37.847255', '2020-07-13 23:34:43.292000', true, 'It is used to test applications, measuring system behaviour and performance under load. LoadRunner can simulate thousands of users concurrently using application software, recording and later analyzing the performance of key components of the application.', '/test/load_runner.png', 'Load runner');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (16, '2020-07-13 21:17:37.847255', '2020-07-13 23:42:29.830000', true, 'Puppet is an open-core software configuration management tool. It runs on many Unix-like systems as well as on Microsoft Windows, and includes its own declarative language to describe system configuration.', '/configure/puppet.png', 'Puppet');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (9, '2020-07-13 21:17:37.847255', '2020-07-13 23:45:34.487000', true, 'is a free (open-source) automated testing framework used to validate web applications across different browsers and platforms. You can use multiple programming languages like Java, C#, Python etc to create Selenium Test Scripts. Testing done using the Selenium tool is usually referred to as Selenium Testing.', '/test/selenium.png', 'Selenium');
INSERT INTO public.tools (tool_id, created_at, updated_at, active, description, image_url, name) VALUES (4, '2020-07-13 21:17:37.847255', '2020-07-13 23:45:46.690000', true, 'Trello is a web-based Kanban-style list-making application', '/plan/trello.png', 'Trello');
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

-- licenses
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (29, '2020-07-13 21:18:59.274894', '2020-07-13 21:18:59.274894', true, 'Enterprise', 'test', 2, 2, 16);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (6, '2020-07-13 21:18:26.350627', '2020-07-13 23:23:18.555000', true, 'Gold', 'Secure & monitor production', 2, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (30, '2020-07-13 21:19:00.384312', '2020-07-13 22:51:52.001000', true, 'STANDARD', 'For enterprise IT operations. 8x5 support', 2, 2, 17);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (31, '2020-07-13 21:19:01.790647', '2020-07-13 22:52:12.335000', true, 'PREMIUM', 'For mission critical DevOps. 24x7 support', 2, 2, 17);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (7, '2020-07-13 21:18:27.178796', '2020-07-13 23:23:41.889000', true, 'Starter', 'Control what goes
into production', 3, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (26, '2020-07-13 21:18:56.243368', '2020-07-13 23:24:18.483000', true, 'MIT License', 'Full access to Jenkins service. Cost depends on hosting service', 3, 3, 14);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (1, '2020-07-13 21:19:07.900444', '2020-07-13 23:02:04.778000', true, 'Basic plan', 'Azure Pipelines: Includes the free offer from INDIVIDUAL SERVICES, Azure Boards: Work item tracking and Kanban boards, Azure Repos: Unlimited private Git repos, Azure Artifacts: 2 GB free per organization', 2, 2, 1);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (2, '2020-07-13 21:18:19.443933', '2020-07-13 23:03:36.672000', true, 'Basic and test plan', 'Includes all Basic plan features, test planning, tracking & execution, browser-based tests with annotation, rich-client test execution, user acceptance testing, centralized reporting', 2, 2, 1);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (3, '2020-07-13 21:18:20.662791', '2020-07-13 23:06:38.759000', true, 'Azure Devops Server', 'Azure DevOps full access hosted in your own server without user limitation', 3, 2, 1);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (27, '2020-07-13 21:18:57.274690', '2020-07-13 23:11:13.245000', true, 'Chef', 'Open source license', 1, 1, 15);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (35, '2020-07-13 21:19:06.244083', '2020-07-13 23:13:52.724000', true, 'APM', 'Find service bottlenecks, search and analyze distributed request traces', 2, 2, 19);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (34, '2020-07-13 21:19:04.697099', '2020-07-13 23:14:13.390000', true, 'Infrastructure', 'Centralize your monitoring of systems and services', 2, 2, 19);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (36, '2020-07-13 21:19:07.087941', '2020-07-13 23:14:55.809000', true, 'Log Management', 'Analyze and explore log data in context with flexible retention', 2, 2, 19);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (22, '2020-07-13 21:18:50.930480', '2020-07-13 23:28:44.217000', true, 'Apache License 2.0', 'Open source tool', 1, 1, 11);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (21, '2020-07-13 21:18:48.399063', '2020-07-13 23:33:33.505000', true, 'Katalon Studio', 'Productive IDE to Generate Automated Tests Easily', 1, 3, 10);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (10, '2020-07-13 21:18:32.210362', '2020-07-13 23:33:56.635000', true, 'STANDARD', 'Counts with 8/5 support', 2, 2, 3);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (16, '2020-07-13 21:18:38.507796', '2020-07-13 23:18:50.677000', true, 'Gradle', 'Open Source license', 1, 3, 5);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (24, '2020-07-13 21:18:54.102655', '2020-07-13 23:19:09.631000', true, 'Team', 'Unlimited public/private repositories, Required reviewers, 3,000 Actions minutes/month, Free for public repositories, 2GB of GitHub Packages storage, Free for public repositories, Code owners', 2, 2, 13);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (25, '2020-07-13 21:18:55.180797', '2020-07-13 23:19:55.380000', true, 'Enterprise', 'Everything included in Team, SAML single sign-on, 50,000 Actions minutes/month, Free for public repositories, 50GB of GitHub Packages storage, Free for public repositories, Advanced auditing', 2, 2, 13);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (14, '2020-07-13 21:18:36.257556', '2020-07-13 23:46:18.732000', true, 'Business Class', 'For small companies', 2, 2, 4);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (4, '2020-07-13 21:18:24.537943', '2020-07-13 23:20:53.653000', true, 'Bronze', 'Control what goes
into production', 2, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (9, '2020-07-13 21:18:29.741452', '2020-07-13 23:21:50.301000', true, 'Ultimate', 'Secure & monitor production', 3, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (5, '2020-07-13 21:18:25.538074', '2020-07-13 23:22:42.604000', true, 'Silver', 'Plan across
multiple teams', 2, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (8, '2020-07-13 21:18:27.991350', '2020-07-13 23:22:51.863000', true, 'Premium', 'Plan across
multiple teams', 3, 2, 2);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (11, '2020-07-13 21:18:33.007292', '2020-07-13 23:34:28.309000', true, 'PREMIUM', 'Counts with 24/7 support', 2, 2, 3);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (12, '2020-07-13 21:18:33.851061', '2020-07-13 23:35:33.417000', true, 'Server', '-Complete control of your environment
-A single server deployment
-Perpetual license + free year of maintenance
', 3, 3, 3);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (13, '2020-07-13 21:18:34.663707', '2020-07-13 23:36:05.700000', true, 'Data Center', 'Same as server with SAML 2.0 support', 3, 3, 3);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (23, '2020-07-13 21:18:51.743120', '2020-07-13 23:36:19.558000', true, 'Free', 'Get 50 virtual user licenses for life', 2, 1, 12);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (17, '2020-07-13 21:18:39.335951', '2020-07-13 23:37:43.507000', true, 'Maven', 'Open source license', 1, 3, 6);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (18, '2020-07-13 21:18:41.398551', '2020-07-13 23:38:57.182000', true, 'Microsoft License', 'Open source tool', 1, 1, 7);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (19, '2020-07-13 21:18:43.398684', '2020-07-13 23:40:28.321000', true, 'Microsoft License', 'Open source tool', 1, 1, 8);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (33, '2020-07-13 21:19:03.697029', '2020-07-13 23:42:36.261000', true, 'Enterprise', 'The Enterprise Edition provides users with additional functionality and includes features that are designed to aid in large-scale configuration, forecasting, and scheduled reporting.', 2, 3, 18);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (32, '2020-07-13 21:19:02.618887', '2020-07-13 23:43:00.321000', true, 'Standard', ' includes twelve months of maintenance (upgrade entitlements) and email support.', 2, 3, 18);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (28, '2020-07-13 21:18:58.102875', '2020-07-13 23:43:26.585000', true, 'Free', 'Open source tool', 1, 1, 16);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (15, '2020-07-13 21:18:37.226435', '2020-07-13 23:44:43.868000', true, 'Enterprise', 'For entreprises', 2, 2, 4);
INSERT INTO public.licenses (license_id, created_at, updated_at, active, name, observation, license_type_id, pay_cycle_id, tool_id) VALUES (20, '2020-07-13 21:18:44.523851', '2020-07-13 23:45:50.763000', true, 'Selenium', 'Open source license', 1, 3, 9);

-- licenses pricing
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (180, '2020-07-13 23:40:28.320000', '2020-07-13 23:40:28.320000', -1, 1, 0, 19);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (217, '2020-07-13 23:42:36.191000', '2020-07-13 23:42:36.191000', -1, 1, 3495, 33);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (181, '2020-07-13 23:43:00.320000', '2020-07-13 23:43:00.320000', -1, 1, 1995, 32);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (219, '2020-07-13 23:43:26.574000', '2020-07-13 23:43:26.574000', 10, 1, 0, 28);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (220, '2020-07-13 23:44:43.859000', '2020-07-13 23:44:43.859000', 20, 1, 20.83, 15);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (221, '2020-07-13 23:44:43.865000', '2020-07-13 23:44:43.865000', 500, 21, 17.5, 15);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (222, '2020-07-13 23:44:43.866000', '2020-07-13 23:44:43.866000', 1000, 501, 12.92, 15);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (223, '2020-07-13 23:44:43.866000', '2020-07-13 23:44:43.866000', 10000, 1001, 4.83, 15);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (182, '2020-07-13 23:45:50.746000', '2020-07-13 23:45:50.746000', -1, 1, 0, 20);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (183, '2020-07-13 23:46:18.731000', '2020-07-13 23:46:18.731000', 100, 1, 9.99, 14);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (50, '2020-07-13 21:20:01.263611', '2020-07-13 21:20:01.263611', -1, 1, 0, 29);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (151, '2020-07-13 22:51:51.867000', '2020-07-13 22:51:51.867000', -1, 1, -1, 30);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (201, '2020-07-13 22:52:12.256000', '2020-07-13 22:52:12.256000', -1, 1, -1, 31);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (205, '2020-07-13 23:02:04.768000', '2020-07-13 23:02:04.768000', 5, 1, 0, 1);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (206, '2020-07-13 23:02:04.774000', '2020-07-13 23:02:04.774000', 20, 6, 6, 1);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (207, '2020-07-13 23:03:36.657000', '2020-07-13 23:03:36.657000', 20, 1, 52, 2);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (152, '2020-07-13 23:06:38.748000', '2020-07-13 23:06:38.748000', -1, 1, -1, 3);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (153, '2020-07-13 23:11:13.231000', '2020-07-13 23:11:13.231000', -1, 1, 0, 27);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (154, '2020-07-13 23:13:52.722000', '2020-07-13 23:13:52.722000', -1, 1, 36, 35);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (208, '2020-07-13 23:14:13.389000', '2020-07-13 23:14:13.389000', 1, 1, 18, 34);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (155, '2020-07-13 23:14:55.807000', '2020-07-13 23:14:55.807000', -1, 1, 1.91, 36);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (102, '2020-07-13 23:18:50.667000', '2020-07-13 23:18:50.667000', -1, 1, 0, 16);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (210, '2020-07-13 23:19:09.611000', '2020-07-13 23:19:09.611000', -1, 1, 4, 24);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (103, '2020-07-13 23:19:55.377000', '2020-07-13 23:19:55.377000', -1, 1, 21, 25);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (158, '2020-07-13 23:20:53.651000', '2020-07-13 23:20:53.651000', -1, 1, 4, 4);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (105, '2020-07-13 23:21:50.299000', '2020-07-13 23:21:50.299000', -1, 1, 99, 9);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (212, '2020-07-13 23:22:42.601000', '2020-07-13 23:22:42.601000', -1, 0, 19, 5);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (107, '2020-07-13 23:22:51.862000', '2020-07-13 23:22:51.862000', -1, 1, 19, 8);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (213, '2020-07-13 23:23:18.552000', '2020-07-13 23:23:18.552000', -1, 1, 99, 6);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (108, '2020-07-13 23:23:41.879000', '2020-07-13 23:23:41.879000', -1, 1, 4, 7);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (214, '2020-07-13 23:24:18.479000', '2020-07-13 23:24:18.479000', -1, 1, 0, 26);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (159, '2020-07-13 23:28:44.213000', '2020-07-13 23:28:44.213000', -1, 1, 0, 22);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (215, '2020-07-13 23:33:33.500000', '2020-07-13 23:33:33.500000', -1, 1, 0, 21);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (160, '2020-07-13 23:33:56.623000', '2020-07-13 23:33:56.623000', 100, 1, 7, 10);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (161, '2020-07-13 23:33:56.623000', '2020-07-13 23:33:56.623000', 250, 101, 6, 10);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (162, '2020-07-13 23:33:56.623000', '2020-07-13 23:33:56.623000', 5000, 251, 5, 10);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (163, '2020-07-13 23:34:28.237000', '2020-07-13 23:34:28.237000', 100, 1, 14, 11);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (164, '2020-07-13 23:34:28.238000', '2020-07-13 23:34:28.238000', 250, 101, 10, 11);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (165, '2020-07-13 23:34:28.238000', '2020-07-13 23:34:28.238000', 5000, 251, 7, 11);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (166, '2020-07-13 23:35:33.414000', '2020-07-13 23:35:33.414000', 10, 1, 10, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (167, '2020-07-13 23:35:33.414000', '2020-07-13 23:35:33.414000', 25, 1, 3500, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (168, '2020-07-13 23:35:33.414000', '2020-07-13 23:35:33.414000', 50, 1, 6800, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (169, '2020-07-13 23:35:33.414000', '2020-07-13 23:35:33.414000', 100, 1, 13300, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (170, '2020-07-13 23:35:33.415000', '2020-07-13 23:35:33.415000', 250, 1, 26400, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (171, '2020-07-13 23:35:33.415000', '2020-07-13 23:35:33.415000', 500, 1, 40000, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (172, '2020-07-13 23:35:33.415000', '2020-07-13 23:35:33.415000', 2000, 1, 60000, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (173, '2020-07-13 23:35:33.415000', '2020-07-13 23:35:33.415000', 10000, 1, 120000, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (174, '2020-07-13 23:35:33.416000', '2020-07-13 23:35:33.416000', -1, 1, 200000, 12);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (175, '2020-07-13 23:36:05.641000', '2020-07-13 23:36:05.641000', 500, 1, 20400, 13);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (176, '2020-07-13 23:36:05.642000', '2020-07-13 23:36:05.642000', 1000, 1, 30000, 13);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (177, '2020-07-13 23:36:05.642000', '2020-07-13 23:36:05.642000', 2000, 1, 52800, 13);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (178, '2020-07-13 23:36:05.642000', '2020-07-13 23:36:05.642000', 3000, 1, 79200, 13);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (179, '2020-07-13 23:36:05.643000', '2020-07-13 23:36:05.643000', 10000, 1, 264000, 13);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (109, '2020-07-13 23:36:19.554000', '2020-07-13 23:36:19.554000', 50, 1, 0, 23);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (110, '2020-07-13 23:37:43.506000', '2020-07-13 23:37:43.506000', -1, 1, 0, 17);
INSERT INTO public.licenses_pricing (license_pricing_id, created_at, updated_at, max_users, min_users, price, license_id) VALUES (216, '2020-07-13 23:38:57.172000', '2020-07-13 23:38:57.172000', -1, 1, 0, 18);


INSERT INTO level (level_id, description, points)
VALUES (1, 'Senior', '3');
INSERT INTO level (level_id, description, points)
VALUES (2, 'Semi senior', '2');
INSERT INTO level (level_id, description, points)
VALUES (3, 'Junior', '1');

INSERT INTO profile (id, description)
VALUES (1, 'Backend Developer');
INSERT INTO profile (id, description)
VALUES (2, 'Quality Assurance');
INSERT INTO profile (id, description)
VALUES (3, 'Frontend Developer');
INSERT INTO profile (id, description)
VALUES (4, 'DevOps Engineer');
INSERT INTO profile (id, description)
VALUES (5, 'DevOps Engineer');