DROP TABLE IF EXISTS exhibitions.orders;

CREATE TABLE IF NOT EXISTS exhibitions.orders (
  id INT AUTO_INCREMENT,
  user_id INT NOT NULL,
  exhibition_id INT NOT NULL,
  seat_amount INT NOT NULL,
  status ENUM('active', 'disabled') NOT NULL,
  PRIMARY KEY (id),
    FOREIGN KEY (exhibition_id) REFERENCES exhibitions.exhibitions (id)
    ON DELETE cascade
    ON UPDATE cascade,
    FOREIGN KEY (user_id)
    REFERENCES exhibitions.users (id)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8mb4;

select * from orders;