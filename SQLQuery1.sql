/* ===========================
   DATABASE QLRapPhim - FULL
   =========================== */
IF DB_ID(N'QLRapPhim') IS NULL
    CREATE DATABASE QLRapPhim;
GO

USE QLRapPhim;
GO

/* ===========================
   1) BẢNG NGƯỜI DÙNG
   =========================== */
IF OBJECT_ID('NguoiDung','U') IS NOT NULL DROP TABLE NguoiDung;
GO
CREATE TABLE NguoiDung (
    MaNguoiDung  INT IDENTITY(1,1) PRIMARY KEY,
    TenNguoiDung NVARCHAR(100) NOT NULL,
    MatKhau      NVARCHAR(255) NOT NULL,
    Email        NVARCHAR(120),
    SDT          NVARCHAR(20),
    VaiTro       NVARCHAR(20) DEFAULT N'USER'
);
GO
select * from HoaDon

/* ===========================
   2) BẢNG PHIM
   =========================== */
IF OBJECT_ID('Phim','U') IS NOT NULL DROP TABLE Phim;
GO
CREATE TABLE Phim(
    MaPhim        INT IDENTITY(1,1) PRIMARY KEY,
    TenPhim       NVARCHAR(200) NOT NULL,
    ThoiLuong     INT,
    DanhGia       NVARCHAR(20),
    TheLoai       NVARCHAR(100),
    MoTa          NVARCHAR(MAX),
    NgayPhatHanh  DATE
);
GO


/* ===========================
   3) BẢNG RẠP
   =========================== */
IF OBJECT_ID('Rap','U') IS NOT NULL DROP TABLE Rap;
GO
CREATE TABLE Rap(
    MaRap     INT IDENTITY(1,1) PRIMARY KEY,
    TenRap    NVARCHAR(150) NOT NULL,
    DiaChi    NVARCHAR(255),
    TrangThaiHoatDong BIT DEFAULT 1
);
GO


/* ===========================
   4) BẢNG GHẾ (XOÁ FK về PhòngChiếu)
   =========================== */
IF OBJECT_ID('Ghe','U') IS NOT NULL DROP TABLE Ghe;
GO
CREATE TABLE Ghe(
    MaGhe      INT IDENTITY(1,1) PRIMARY KEY,
    HangGhe    NVARCHAR(5),
    SoGhe      INT,
    SoHang     INT,
    LoaiGhe    NVARCHAR(20),
    TrangThai  NVARCHAR(30)
);
GO


/* ===========================
   5) BẢNG SUẤT CHIẾU (XOÁ MaPhong)
   =========================== */
IF OBJECT_ID('SuatChieu','U') IS NOT NULL DROP TABLE SuatChieu;
GO
CREATE TABLE SuatChieu(
    MaSuatChieu INT IDENTITY(1,1) PRIMARY KEY,
    MaPhim      INT NOT NULL,
    BatDau      DATETIME NOT NULL,
    KetThuc     DATETIME NOT NULL,
    TrangThai   NVARCHAR(20),

    FOREIGN KEY (MaPhim) REFERENCES Phim(MaPhim)
);
GO


/* ===========================
   6) BẢNG HÓA ĐƠN
   =========================== */
IF OBJECT_ID('HoaDon','U') IS NOT NULL DROP TABLE HoaDon;
GO
CREATE TABLE HoaDon(
    MaHoaDon   INT IDENTITY(1,1) PRIMARY KEY,
    MaNguoiDung INT,
	MaVe INT,
	MaCombo INT,
    NgayDat     DATETIME DEFAULT GETDATE(),
    TongTien    DECIMAL(12,2),
    PhuongThucThanhToan NVARCHAR(20),

    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
);
GO
select * from Phim

/* ===========================
   7) BẢNG VÉ (XOÁ MaPhong)
   =========================== */
IF OBJECT_ID('Ticket','U') IS NOT NULL DROP TABLE Ticket;
GO
CREATE TABLE Ticket(
    MaVe        INT IDENTITY(1,1) PRIMARY KEY,
    TenPhim     NVARCHAR(200),
    SuatChieu   NVARCHAR(50),
    Ghe         NVARCHAR(10),
    GiaVe       DECIMAL(12,2),
	MoTa        NVARCHAR(30),

    MaNguoiDung INT,
    MaPhim      INT,
    MaSuatChieu INT,
    MaRap       INT,
    MaHoaDon    INT,

    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung),
    FOREIGN KEY (MaPhim)      REFERENCES Phim(MaPhim),
    FOREIGN KEY (MaSuatChieu) REFERENCES SuatChieu(MaSuatChieu),
    FOREIGN KEY (MaRap)       REFERENCES Rap(MaRap),
    FOREIGN KEY (MaHoaDon)    REFERENCES HoaDon(MaHoaDon)
);
GO

/* ===========================
   8) BẢNG COMBO
   =========================== */
IF OBJECT_ID('Combo','U') IS NOT NULL DROP TABLE Combo;
GO
CREATE TABLE Combo(
    MaCombo  INT IDENTITY(1,1) PRIMARY KEY,
    TenCombo NVARCHAR(150),
    MoTa     NVARCHAR(255),
    GiaBan   DECIMAL(12,2)
);
GO


/* ===========================
   9) BẢNG TRUNG GIAN Combo - Hóa Đơn
   =========================== */
IF OBJECT_ID('HoaDon_ComboDoAn','U') IS NOT NULL DROP TABLE HoaDon_ComboDoAn;
GO
CREATE TABLE HoaDon_ComboDoAn(
    MaHoaDon INT,
    MaCombo  INT,
    PRIMARY KEY (MaHoaDon, MaCombo),

    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
    FOREIGN KEY (MaCombo)  REFERENCES Combo(MaCombo)
);
GO
INSERT INTO NguoiDung (TenNguoiDung, MatKhau, Email, SDT, VaiTro)
VALUES
(N'admin',       N'admin123',      N'admin@gmail.com',      '0901234567', 'ADMIN'),
(N'QuanLyRap',   N'ql123456',      N'quanlyrap@gmail.com',  '0908765432', 'STAFF'),
(N'NhanVienA',   N'nv123456',      N'nhanvienA@gmail.com',  '0987654321', 'STAFF'),
(N'PhuongUser',  N'123456',        N'phuong@gmail.com',     '0911112222', 'USER'),
(N'HoaiUser',    N'123456',        N'hoai@gmail.com',       '0922223333', 'USER'),
(N'DemoUser',    N'123456',        N'demo.user@gmail.com',  '0933334444', 'USER');

select * from HoaDon

INSERT INTO Rap(TenRap, DiaChi)
VALUES
(N'Galaxy TimeCity',     N'458 Minh Khai'),
(N'Galaxy Nguyễn Trãi',  N'123 Nguyễn Trãi'),
(N'Galaxy Giảng Võ',     N'80 Giảng Võ'),
(N'Galaxy Long Biên',    N'212 Nguyễn Văn Cừ'),
(N'Galaxy Mỹ Đình',      N'16 Phạm Hùng');

INSERT INTO Ghe (HangGhe, SoGhe, SoHang, LoaiGhe, TrangThai)
VALUES
('A', 1, 1, N'Thường', N'Còn trống'),
('A', 2, 1, N'Thường', N'Đã đặt'),
('B', 1, 2, N'VIP',    N'Còn trống'),
('B', 2, 2, N'VIP',    N'Còn trống'),
('C', 3, 3, N'Thường', N'Đang bảo trì');

INSERT INTO Phim(TenPhim, ThoiLuong, TheLoai, DanhGia, MoTa, NgayPhatHanh)
VALUES 
(N'Nhà Bà Nữ',110,N'Hài',N'C13', N'Phim Việt Nam', '2023-01-15'),
(N'Avengers: Endgame',181,N'Hành động',N'C13', N'Avengers trận chiến cuối', '2019-04-26'),
(N'Gặp Lại Chị Bầu',105,N'Tâm lý, Hài',N'C13',N'Phim tình cảm Việt Nam', '2024-02-10'),
(N'Dune: Part Two',166,N'Khoa học viễn tưởng',N'C16',N'Sa mạc Arrakis trở lại', '2024-03-01'),
(N'Inside Out 2',110,N'Hoạt hình',N'P',N'Cảm xúc tuổi dậy thì', '2024-06-14');
INSERT INTO SuatChieu(MaPhim, BatDau, KetThuc, TrangThai)
VALUES
(1, '2025-12-01 14:00', '2025-12-01 16:00', N'OPEN'),
(2, '2025-12-01 18:30', '2025-12-01 21:00', N'OPEN'),
(3, '2025-12-02 10:00', '2025-12-02 12:00', N'OPEN'),
(4, '2025-12-02 15:00', '2025-12-02 17:30', N'OPEN'),
(5, '2025-12-03 19:00', '2025-12-03 21:00', N'OPEN');
INSERT INTO Combo(TenCombo, MoTa, GiaBan)
VALUES
(N'Combo 1', N'1 bắp + 1 nước', 50000),
(N'Combo 2', N'2 bắp', 60000),
(N'Combo 3', N'1 bắp + 2 nước', 65000),
(N'Combo 4', N'2 bắp + 2 nước', 85000),
(N'Combo 5', N'1 bắp lớn + 1 nước lớn', 75000);
INSERT INTO Ticket (TenPhim, SuatChieu, Ghe, GiaVe, MoTa,
                    MaNguoiDung, MaPhim, MaSuatChieu, MaRap, MaHoaDon)
VALUES
(N'Hai Phượng', N'14:00 - 16:00', N'A1', 85000, N'Vé 1', 1, 1, 1, 1, 1),
(N'Bố Già',     N'19:00 - 21:00', N'B5', 95000, N'Vé 2', 2, 2, 2, 1, 2),
(N'Mắt Biếc',   N'20:30 - 23:00', N'C3', 90000, N'Vé 3', 3, 3, 3, 2, 3),
(N'Em Chưa 18', N'16:45 - 19:00', N'D7', 85000, N'Vé 4', 1, 4, 4, 3, 4),
(N'Tèo Em',     N'21:00 - 00:30', N'E12',110000, N'Vé 5', 4, 5, 5, 2, 5);

INSERT INTO HoaDon(MaNguoiDung, MaVe, MaCombo, NgayDat, TongTien, PhuongThucThanhToan)
VALUES
(1, 1, 1, '2024-01-15 14:30', 250000, N'Tiền mặt'),
(2, 2, 2, '2024-01-16 16:45', 320000, N'Chuyển khoản'),
(3, 3, 3, '2024-01-17 19:15', 180000, N'Thẻ tín dụng'),
(4, 4, 4, '2024-01-18 20:30', 420000, N'Ví điện tử'),
(5, 5, 5, '2024-01-19 21:00', 290000, N'Tiền mặt');
INSERT INTO HoaDon_ComboDoAn (MaHoaDon, MaCombo)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

select *from HoaDon

SELECT COUNT(*) AS TongSoVeTrongNgay
FROM HoaDon
WHERE CAST(NgayDat AS DATE) = CAST(GETDATE() AS DATE);

SELECT SUM(TongTien) AS DoanhThu_ThangHienTai
FROM HoaDon
WHERE MONTH(NgayDat) = MONTH(GETDATE())
  AND YEAR(NgayDat) = YEAR(GETDATE());

SELECT COUNT(*) AS SoSuatChieuDangMo
FROM SuatChieu
WHERE TrangThai = N'OPEN';


SELECT COUNT(DISTINCT MaNguoiDung) AS KhachHangDaMuaVe
FROM HoaDon;

SELECT TOP 5
    P.TenPhim,
    SUM(HD.TongTien) AS TongDoanhThu
FROM HoaDon HD
JOIN Ticket T ON HD.MaVe = T.MaVe
JOIN Phim P ON T.MaPhim = P.MaPhim
GROUP BY P.TenPhim
ORDER BY TongDoanhThu DESC;

SELECT 
    COUNT(*) AS TongVe,
    SUM(TongTien) AS TongDoanhThu
FROM HoaDon
WHERE NgayDat >= '2024-01-01'
  AND NgayDat <  '2025-01-01';

  select * from HoaDon