CREATE TABLE IF NOT EXISTS participants (
  id UUID NOT NULL PRIMARY KEY,
  social VARCHAR(255),
  nick VARCHAR(255),
  hash varchar(255) NOT NULL,
  raffleId UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS winners (
  id UUID NOT NULL PRIMARY KEY,
  participantId UUID NOT NULL REFERENCES participants (id),
  raffleId UUID NOT NULL,
  ordering integer,
  isValid boolean NOT NULL DEFAULT true,
  createdAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
