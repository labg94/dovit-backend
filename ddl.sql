create table companies
(
    company_id bigserial not null
        constraint companies_pkey
            primary key,
    name varchar(255)
);

alter table companies owner to dovit;

create table devops_categories
(
    id bigint not null
        constraint devops_categories_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    active boolean default true,
    description varchar(255)
);

alter table devops_categories owner to dovit;

create table devops_subcategories
(
    devops_subcategory_id bigint not null
        constraint devops_subcategories_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    active boolean default true,
    description varchar(255),
    devops_category_id bigint not null
        constraint fk4s3deqvsifk0ol8x9tvor0ql3
            references devops_categories
);

alter table devops_subcategories owner to dovit;

create table level
(
    level_id bigint not null
        constraint level_pkey
            primary key,
    description varchar(255),
    points integer
);

alter table level owner to dovit;

create table license_pay_cycle
(
    id bigint not null
        constraint license_pay_cycle_pkey
            primary key,
    description varchar(255)
);

alter table license_pay_cycle owner to dovit;

create table license_type
(
    id bigint not null
        constraint license_type_pkey
            primary key,
    description varchar(255)
);

alter table license_type owner to dovit;

create table member_available_status
(
    id bigint not null
        constraint member_available_status_pkey
            primary key,
    description varchar(255)
);

alter table member_available_status owner to dovit;

create table members
(
    id bigserial not null
        constraint members_pkey
            primary key,
    active boolean,
    last_name varchar(255),
    name varchar(255),
    company_company_id bigint
        constraint fkflqvm5vyah8tymy6m8nsuqhp4
            references companies
);

alter table members owner to dovit;

create table profile
(
    id bigint not null
        constraint profile_pkey
            primary key,
    description varchar(255)
);

alter table profile owner to dovit;

create table member_profiles
(
    member_id bigint not null
        constraint fkj315abfuirqvdyywpugguunft
            references members,
    profile_id bigint not null
        constraint fkk57ifhjewwbsag7tx66mqkeqm
            references profile
);

alter table member_profiles owner to dovit;

create table project
(
    id bigint not null
        constraint project_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    end_date timestamp,
    finished boolean,
    name varchar(255),
    observation varchar(255),
    start timestamp,
    company_company_id bigint
        constraint fkn9jrgitselvn7vsjdjtveecyq
            references companies
);

alter table project owner to dovit;

create table pipeline
(
    id bigint not null
        constraint pipeline_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    cost double precision,
    recommended boolean not null,
    project_id bigint
        constraint fkcoe0iaqma8pr94p6n1edd8dsd
            references project
);

alter table pipeline owner to dovit;

create table project_members
(
    devops_category_id bigint not null
        constraint fkrue6ty026tvk7480em4ihbdha
            references devops_categories,
    member_id bigint not null
        constraint fkii93vgbt0yhed0rqcmlt41p9f
            references members,
    project_id bigint not null
        constraint fki28gx2d4xrrhtrfnk12aef1e4
            references project,
    created_at timestamp,
    updated_at timestamp,
    constraint project_members_pkey
        primary key (devops_category_id, member_id, project_id)
);

alter table project_members owner to dovit;

create table project_type
(
    id bigint not null
        constraint project_type_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    description varchar(255) not null,
    observation varchar(255)
);

alter table project_type owner to dovit;

create table project_project_types
(
    project_id bigint not null
        constraint fkbip9cglmp8i9p3e1vjkedjk2v
            references project,
    project_type_id bigint not null
        constraint fk5jv8s0momsx6l5wiwltaik5ie
            references project_type
);

alter table project_project_types owner to dovit;

create table roles
(
    id bigint not null
        constraint roles_pkey
            primary key,
    name varchar(255)
);

alter table roles owner to dovit;

create table tools
(
    tool_id bigint not null
        constraint tools_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    active boolean default true,
    description varchar(255),
    image_url varchar(255),
    name varchar(255)
);

alter table tools owner to dovit;

create table licenses
(
    license_id bigint not null
        constraint licenses_pkey
            primary key,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    active boolean default true,
    name varchar(255),
    observation varchar(255),
    license_type_id bigint not null
        constraint fkf4o0e4yp49ptucu55od15f2cu
            references license_type,
    pay_cycle_id bigint not null
        constraint fkjgoov4t8rrfsh9lyo5nvxg3i2
            references license_pay_cycle,
    tool_id bigint not null
        constraint fkmuwa2hmxq68d1nss7liyofcf5
            references tools
);

alter table licenses owner to dovit;

create table company_licenses
(
    company_license_id bigserial not null
        constraint company_licenses_pkey
            primary key,
    expiration_date timestamp,
    start_date timestamp not null,
    company_id bigint
        constraint fk8dw7nk1sj918vrjxndu31tl51
            references companies,
    license_id bigint
        constraint fk3v3gxf7nqynf40yfnrab934si
            references licenses
);

alter table company_licenses owner to dovit;

create table licenses_pricing
(
    license_pricing_id bigint not null
        constraint licenses_pricing_pkey
            primary key,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    max_users bigint,
    min_users bigint,
    price double precision,
    license_id bigint not null
        constraint fk7b69kfo7fkthigcxkqcm3g6ob
            references licenses
);

alter table licenses_pricing owner to dovit;

create table pipeline_tool
(
    category_id bigint not null
        constraint fk72x5ollvpq2bqkyjyqhfkaous
            references devops_categories,
    pipeline_id bigint not null
        constraint fk2qklvl2swr8t1ei6elq03y7r9
            references pipeline,
    tool_id bigint not null
        constraint fk61u3h65gsxon7hmu6ubvkirat
            references tools,
    created_at timestamp,
    updated_at timestamp,
    log jsonb,
    constraint pipeline_tool_pkey
        primary key (category_id, pipeline_id, tool_id)
);

alter table pipeline_tool owner to dovit;

create table tool_profile
(
    level_id bigint not null
        constraint fkcchgx29g2p10c9m1cxskutts
            references level,
    member_id bigint not null
        constraint fk5jfunvbxuhr2np1u8itjtufa3
            references members,
    tool_id bigint not null
        constraint fkhsq393krpfae5hiwvjotw18ot
            references tools,
    constraint tool_profile_pkey
        primary key (level_id, member_id, tool_id)
);

alter table tool_profile owner to dovit;

create table tool_project_type
(
    project_type_id bigint not null
        constraint fks5o60kx51a9sjbasjohur87l7
            references project_type,
    tool_id bigint not null
        constraint fkeias37k2ktrj36s4efj74ld4u
            references tools,
    created_at timestamp,
    updated_at timestamp,
    constraint tool_project_type_pkey
        primary key (project_type_id, tool_id)
);

alter table tool_project_type owner to dovit;

create table tool_subcategory
(
    tool_id bigint not null
        constraint fk54l2j70yqhph55doxl9jn3uso
            references tools,
    subcategory_id bigint not null
        constraint fk4rfrv6rkdovj5ufy1jn6hc3u7
            references devops_subcategories
);

alter table tool_subcategory owner to dovit;

create table users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    active boolean default true,
    email varchar(255)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    last_name varchar(255),
    name varchar(255),
    password varchar(255),
    rut varchar(255)
        constraint ukscuj1snh0iy35s195t3qff5o
            unique,
    company_id bigint
        constraint fkin8gn4o1hpiwe6qe4ey7ykwq7
            references companies,
    role_id bigint
        constraint fkp56c1712k691lhsyewcssf40f
            references roles
);

alter table users owner to dovit;

create table audit
(
    audit_id bigserial not null
        constraint audit_pkey
            primary key,
    action_date timestamp,
    data jsonb,
    database_user varchar(255),
    message varchar(255),
    status varchar(255),
    user_id bigint
        constraint fkn2jr87wh10l0fmmox82athugs
            references users
);

alter table audit owner to dovit;

create index status_index
    on audit (status);

create table suggestion_mailbox
(
    suggestion_id bigint not null
        constraint suggestion_mailbox_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    message varchar(1000),
    tool varchar(255),
    viewed boolean default false,
    category_id bigint
        constraint fkjtjsv9flts1hajbusrt3a0951
            references devops_categories,
    subcategory_id bigint
        constraint fk7i58km77br5yjw6vo20gqg6ev
            references devops_subcategories,
    suggested_by_id bigint
        constraint fkqnecbbdls05kyp45r2jq404hr
            references users
);

alter table suggestion_mailbox owner to dovit;

