create table booking
(
    booking_id    varchar(20) not null
        primary key,
    date          date        null,
    type          int         null,
    Hotel_id      varchar(20) null,
    hotel_hour    int         null,
    Vehicale_id   varchar(20) null,
    vehicale_hour int         null,
    Total         double      null,
    constraint booking_room_room_id_fk
        foreign key (Hotel_id) references room (room_id)
);

create table employee
(
    employee_id    varchar(20) not null
        primary key,
    name           varchar(50) null,
    birthday       date        null,
    address        varchar(20) null,
    age            int         null,
    contact_number int         null,
    salary         double      null,
    Type           varchar(20) null
);

create table employee_report
(
    report_id   varchar(20) not null
        primary key,
    date        date        null,
    employee_ID varchar(20) not null,
    constraint employee_ID
        foreign key (employee_ID) references employee (employee_id)
);

create table member
(
    member_id varchar(20) not null
        primary key,
    name      varchar(20) null,
    address   varchar(20) null,
    mail      varchar(30) null
);

create table payment
(
    Pay_id     varchar(20) not null
        primary key,
    member_id  varchar(20) null,
    booking_id varchar(20) null,
    Date       date        null,
    total      double      null,
    constraint Payment_member_member_id_fk
        foreign key (member_id) references member (member_id),
    constraint bookingID
        foreign key (booking_id) references booking (booking_id)
);

create table reportmanage
(
    Employee_Id varchar(20) null,
    Report_id   varchar(20) null,
    constraint ReportManage_employee_employee_id_fk
        foreign key (Employee_Id) references employee (employee_id),
    constraint ReportManage_employee_report_report_id_fk
        foreign key (Report_id) references employee_report (report_id)
);

create table room
(
    room_id     varchar(20) not null
        primary key,
    type        varchar(20) null,
    locathion   varchar(20) null,
    room_number varchar(30) null,
    price       double      null,
    Image       longblob    null
);

create table room_manage
(
    Room_id    varchar(20) null,
    Booking_id varchar(20) null,
    constraint room_mange_booking_booking_id_fk
        foreign key (Booking_id) references booking (booking_id),
    constraint room_mange_room_room_id_fk
        foreign key (Room_id) references room (room_id)
);

create table user
(
    user_id  varchar(20) not null
        primary key,
    name     varchar(50) null,
    mail     varchar(50) null,
    password varchar(20) null
);

create table vehicalemanage
(
    v_id      varchar(20) null,
    Member_id varchar(20) null,
    constraint VehicaleManage_member_member_id_fk
        foreign key (Member_id) references member (member_id),
    constraint VehicaleManage_vehicle_vehicle_id_fk
        foreign key (v_id) references vehicle (vehicle_id)
);

create table vehicle
(
    vehicle_id varchar(20) not null,
    price      double      null,
    type       varchar(20) null,
    number     int         null,
    Image      longblob    null
);

create index vehicle_vehicle_id_index
    on vehicle (vehicle_id);




