-- Flyway Baseline Migration Script
-- 화장실 예약 시스템 초기 스키마

-- 1. lavatory 테이블 (화장실실)
CREATE TABLE IF NOT EXISTS lavatory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sex ENUM('MEN', 'WOMEN', 'ETC') NOT NULL,
    description VARCHAR(255) NOT NULL
);

-- 2. member 테이블 (회원)
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    sex ENUM('MEN', 'WOMEN', 'ETC') NOT NULL
);

-- 3. toilet 테이블 (화장실)
CREATE TABLE IF NOT EXISTS toilet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    is_bidet BOOLEAN NOT NULL DEFAULT FALSE,
    lavatory_id BIGINT NOT NULL,
    FOREIGN KEY (lavatory_id) REFERENCES lavatory(id) ON DELETE CASCADE
);

-- 4. reservation 테이블 (예약)
CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    toilet_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    alias VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (toilet_id) REFERENCES toilet(id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX IF NOT EXISTS idx_member_email ON member(email);
CREATE INDEX IF NOT EXISTS idx_toilet_lavatory_id ON toilet(lavatory_id);
CREATE INDEX IF NOT EXISTS idx_reservation_member_id ON reservation(member_id);
CREATE INDEX IF NOT EXISTS idx_reservation_toilet_id ON reservation(toilet_id);
CREATE INDEX IF NOT EXISTS idx_reservation_time ON reservation(start_time, end_time);

-- 초기 데이터 삽입 (기존 데이터가 있다면 무시)
INSERT IGNORE INTO lavatory (id, sex, description) VALUES 
(1, 'MEN', '잠실굿샷남자화장실');

INSERT IGNORE INTO toilet (id, description, is_bidet, lavatory_id) VALUES 
(1, NULL, FALSE, 1);
