INSERT INTO participants
  (id, social, hash, nick, raffleId)
VALUES
  ('14d66df8-9520-11e8-9eb6-529269fb1459', 'somebody@heremail.com', '20909jffsd', 'John Doe', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO participants
  (id, social, hash, nick, raffleId)
VALUES
  ('14d67096-9520-11e8-9eb6-529269fb1459', 'somebodyelse@heremail.com', '20909jffsd', 'John Doe', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc');

INSERT INTO winners
  (id, participantId, raffleId, ordering)
VALUES
  ('14d671d6-9520-11e8-9eb6-529269fb1459', '14d66df8-9520-11e8-9eb6-529269fb1459', 'cc00c00e-6a42-11e8-adc0-fa7ae01bbebc', 1);
