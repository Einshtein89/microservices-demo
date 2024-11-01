create table if not exists analytics.twitter_analytics
(
    id          uuid    not null
        primary key,
    word        varchar not null,
    word_count  bigint  not null,
    record_date time with time zone,
    CONSTRAINT pk_twitter_analytics PRIMARY KEY (id)
);

alter table analytics.twitter_analytics
    owner to postgres;

create index if not exists "INDX_WORD_BY_DATE"
    on analytics.twitter_analytics (word asc, record_date desc);