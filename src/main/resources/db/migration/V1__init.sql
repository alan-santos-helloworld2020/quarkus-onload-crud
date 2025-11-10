create table cliente (
  id bigserial primary key,
  nome varchar(120) not null,
  email varchar(160) not null unique,
  telefone varchar(20),
  cep varchar(9)
);


