CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi người dùng.
    email NVARCHAR(100) NOT NULL UNIQUE, -- Địa chỉ email của người dùng - Tên đăng nhạp luôn.
    password NVARCHAR(255) NOT NULL, -- Mật khẩu của người dùng (thường được mã hóa).
    full_name NVARCHAR(100), -- Họ và tên đầy đủ của người dùng.
    address NVARCHAR(255), -- Địa chỉ của người dùng.
    phone_number NVARCHAR(15)NOT NULL UNIQUE, -- Số điện thoại của người dùng.
    role NVARCHAR(50) DEFAULT 'customer' CHECK (role IN ('customer', 'admin')) -- Vai trò của người dùng (`customer` hoặc `admin`).
)
CREATE TABLE Authors (
    author_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi tác giả.
    author_name NVARCHAR(100) NOT NULL UNIQUE, -- Tên của tác giả.
    date_of_birth DATE -- Ngày sinh của tác giả.
)
CREATE TABLE Books (
    book_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi cuốn sách.
    title NVARCHAR(255) NOT NULL, -- Tiêu đề của sách.(tên sách)
    image_url NVARCHAR(255), -- Link ảnh bìa sách.
    category NVARCHAR(100), -- Thể loại của sách (ví dụ: Khoa học, Văn học).
    price DECIMAL(10, 2) NOT NULL, -- Giá bán của sách.
    author_id INT, -- Khóa ngoại, liên kết với bảng `Authors` để xác định tác giả của sách.
    description TEXT, -- Mô tả chi tiết về sách.
    publisher NVARCHAR(100), -- Nhà xuất bản của sách.
    publication_year INT, -- Năm xuất bản của sách.
    dimensions NVARCHAR(50), -- Kích thước của sách (ví dụ: "14x21 cm").
    FOREIGN KEY (author_id) REFERENCES Authors(author_id) -- Liên kết khóa ngoại với bảng `Authors`.
)
-- CREATE TABLE Carts (
--     cart_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi giỏ hàng.
--     user_id INT NOT NULL, -- Khóa ngoại, liên kết với bảng `Users` để xác định người dùng sở hữu giỏ hàng.
--     created_at DATETIME DEFAULT GETDATE(), -- Thời gian tạo giỏ hàng (mặc định là thời gian hiện tại).
--     book_id INT NOT NULL, -- Khóa ngoại, liên kết với bảng `Books` để xác định sách trong giỏ hàng.
--     quantity INT NOT NULL, -- Số lượng sách trong giỏ hàng.
--     status NVARCHAR(50) DEFAULT 'pending' CHECK (status IN ('pending', 'ordered', 'paid')),
--     FOREIGN KEY (user_id) REFERENCES Users(user_id) -- Liên kết khóa ngoại với bảng `Users`.
--     FOREIGN KEY (book_id) REFERENCES Books(book_id) -- Liên kết khóa ngoại với bảng `Books`.
-- )
CREATE TABLE Carts (
    cart_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    quantity INT NOT NULL CHECK (quantity > 0),
    status NVARCHAR(50) DEFAULT 'pending' CHECK (status IN ('pending', 'ordered', 'paid')),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
)

-- CREATE TABLE Cart_Items (
--     cart_item_id INT IDENTITY(1,1) PRIMARY KEY,
--     cart_id INT NOT NULL,
    
    
--     FOREIGN KEY (cart_id) REFERENCES Carts(cart_id),
    
-- )
CREATE TABLE Orders (
    order_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi đơn hàng.
    user_id INT NOT NULL, -- Khóa ngoại, liên kết với bảng `Users` để xác định người dùng đặt hàng.
    order_date DATETIME DEFAULT GETDATE(), -- Ngày đặt hàng (mặc định là thời gian hiện tại).
    total_amount DECIMAL(10, 2) NOT NULL, -- Tổng số tiền của đơn hàng.
    status NVARCHAR(50) DEFAULT 'pending' CHECK (status IN ('pending', 'shipped', 'delivered', 'cancelled')), -- Trạng thái đơn hàng.
    discount_id INT NULL, -- Khóa ngoại, liên kết với bảng `Discounts` để áp dụng mã giảm giá (có thể NULL nếu không có giảm giá).
    FOREIGN KEY (user_id) REFERENCES Users(user_id), -- Liên kết khóa ngoại với bảng `Users`.
    FOREIGN KEY (discount_id) REFERENCES Discounts(discount_id) -- Liên kết khóa ngoại với bảng `Discounts`.
)
CREATE TABLE Payments (
    payment_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    cart_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    qr_code NVARCHAR(255) NOT NULL, -- Mã QR hiển thị để thanh toán
    status NVARCHAR(50) DEFAULT 'pending' CHECK (status IN ('pending', 'completed', 'failed')),
    payment_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (cart_id) REFERENCES Carts(cart_id)
)
CREATE TABLE Discounts (
    discount_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính, tự động tăng, định danh duy nhất cho mỗi mã giảm giá.
    discount_name NVARCHAR(100) NOT NULL, -- Tên của mã giảm giá.
    discount_code NVARCHAR(50) NOT NULL UNIQUE, -- Code mã giảm giá (phải là duy nhất).
    book_id INT NOT NULL, -- Khóa ngoại, liên kết với bảng `Books` để xác định sách.
    min_order_amount DECIMAL(10, 2) DEFAULT 0, -- Mức chi tối thiểu để áp dụng mã giảm giá.
    start_date DATE NOT NULL, -- Ngày bắt đầu áp dụng mã giảm giá.
    end_date DATE NOT NULL, -- Ngày kết thúc áp dụng mã giảm giá.
    CHECK (end_date >= start_date), -- Đảm bảo ngày kết thúc không nhỏ hơn ngày bắt đầu.
    FOREIGN KEY (book_id) REFERENCES Books(book_id) -- Liên kết khóa ngoại với bảng `Books`.
)