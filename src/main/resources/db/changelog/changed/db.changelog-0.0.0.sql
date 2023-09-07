-- liquibase formatted sql
-- changeset author:aesop0817 context:test,local,dev,prod,master

CREATE TABLE public.member
(
    member_id          varchar(50) NOT NULL,
    member_name        varchar(50) NOT NULL,
    join_date          TIMESTAMP,
    modify_date        TIMESTAMP,
    rank               varchar(50),
    blocked            boolean,
    total_point        int,
    max_rent_count     int,
    current_rent_count int,
    CONSTRAINT user_pk PRIMARY KEY (member_id),
    CONSTRAINT user_id UNIQUE (member_id)
);
comment on column member.member_id is '회원 아이디';
comment on column member.member_name is '회원 이름';
comment on column member.join_date is '회원 가입일';
comment on column member.modify_date is '회원정보 수정일';
comment on column member.rank is '회원등급';
comment on column member.blocked is '회원 자격 정지 여부';
comment on column member.total_point is '회원이 보유한 총 포인트';
comment on column member.max_rent_count is '회원이 빌릴 수 있는 최대 대여권수';
comment on column member.current_rent_count is '회원이 현재 대여한 권수';