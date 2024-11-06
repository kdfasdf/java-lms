alter table sessions add column session_register_status varchar(20) not null;
alter table students add column select_status varchar(20) not null;

update sessions set sessions_register_status =
    case session_status when 'ready' then 'open'
when 'register' then 'open'
when 'closed' then 'finished';

update student set select_status = 'selected';

alter table students alter column select_status set default 'unselected'
