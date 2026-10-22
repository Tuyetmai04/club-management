# Hệ thống Quản lý Câu lạc bộ

Ứng dụng web quản lý hoạt động câu lạc bộ được xây dựng bằng Java, Spring Boot và MySQL. Hệ thống được triển khai bằng Docker Compose, sử dụng Nginx làm reverse proxy, đồng thời tích hợp các công cụ giám sát và quản lý log tập trung.

## 1. Công nghệ sử dụng

- **Backend:** Java 21, Spring Boot, Spring Data JPA
- **Giao diện:** Thymeleaf, Bootstrap
- **Cơ sở dữ liệu:** MySQL 8
- **Triển khai:** Docker, Docker Compose
- **Reverse proxy và HTTPS:** Nginx
- **Giám sát:** Prometheus, Grafana, cAdvisor
- **Quản lý log:** Loki, Promtail
- **Quản trị cơ sở dữ liệu:** phpMyAdmin

## 2. Chức năng chính

- Quản lý thành viên câu lạc bộ.
- Quản lý sự kiện.
- Quản lý các khoản đóng góp và quỹ câu lạc bộ.
- Quản lý lịch họp.
- Hiển thị thông tin tổng quan trên trang Dashboard.

## 3. Yêu cầu môi trường

- Windows, Linux hoặc macOS.
- Docker Desktop hoặc Docker Engine có hỗ trợ Docker Compose.
- Git để tải mã nguồn.

Nếu chạy ứng dụng trực tiếp ngoài Docker, cần cài thêm Java 21, Maven và MySQL tương thích.

## 4. Cài đặt và khởi chạy

### Bước 1: Lấy mã nguồn

Nếu đã cài Git, chạy:

```bash
git clone <GITHUB_REPOSITORY_URL>
cd club-management
```

Thay `<GITHUB_REPOSITORY_URL>` bằng địa chỉ repository thực tế sau khi dự án được đẩy lên GitHub.

### Bước 2: Tạo file cấu hình môi trường

Trên PowerShell:

```powershell
Copy-Item .env.example .env
```

Mở file `.env` và thay các giá trị mẫu bằng thông tin phù hợp với môi trường của bạn. Cần đặt mật khẩu đủ mạnh cho MySQL và tài khoản quản trị Grafana.

Không đưa file `.env` chứa thông tin thật lên GitHub.

### Bước 3: Khởi chạy hệ thống

Tại thư mục chứa `docker-compose.yml`, chạy:

```bash
docker compose up -d --build
```

Kiểm tra trạng thái các dịch vụ:

```bash
docker compose ps
```

Xem log khi cần kiểm tra lỗi:

```bash
docker compose logs -f
```

### Bước 4: Truy cập các dịch vụ

Các cổng mặc định được cấu hình trong `.env.example`:

| Thành phần | Địa chỉ |
|---|---|
| Ứng dụng qua Nginx (HTTP) | http://localhost:8081 |
| Ứng dụng qua Nginx (HTTPS) | https://localhost:8443 |
| Ứng dụng Spring Boot trực tiếp | http://localhost:8080 |
| phpMyAdmin | http://localhost:8082 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |
| cAdvisor | http://localhost:8083 |

HTTPS sử dụng chứng chỉ tự ký phục vụ môi trường thử nghiệm, vì vậy trình duyệt có thể hiển thị cảnh báo chứng chỉ.

Thông tin đăng nhập Grafana và MySQL được cấu hình thông qua file `.env`. Không sử dụng mật khẩu mẫu trong môi trường thực tế.

## 5. Giám sát và quản lý log

- Prometheus thu thập các metrics từ những target đã được cấu hình.
- Grafana trực quan hóa dữ liệu giám sát thông qua dashboard.
- cAdvisor cung cấp metrics về tài nguyên container.
- Promtail thu thập log từ các nguồn được cấu hình và chuyển đến Loki.
- Loki lưu trữ log, hỗ trợ truy vấn bằng LogQL thông qua Grafana.

Phạm vi metrics và log thực tế phụ thuộc vào cấu hình các dịch vụ trong thư mục `monitoring/`.

## 6. Dừng hệ thống

Dừng các container:

```bash
docker compose down
```

Dữ liệu trong các Docker volume được giữ lại khi chỉ chạy lệnh trên. Không xóa volume nếu cần bảo toàn dữ liệu cơ sở dữ liệu, dashboard hoặc log đã lưu.

## 7. Cấu trúc dự án

```text
club-management/
├── src/                       # Mã nguồn ứng dụng
├── monitoring/
│   ├── prometheus/             # Cấu hình Prometheus
│   ├── grafana/                # Dashboard và provisioning
│   ├── loki/                   # Cấu hình Loki
│   └── promtail/               # Cấu hình Promtail
├── nginx/                      # Cấu hình Nginx và SSL
├── Dockerfile                  # Đóng gói ứng dụng
├── docker-compose.yml          # Triển khai các dịch vụ
├── pom.xml                     # Cấu hình Maven
├── .env.example                # Mẫu biến môi trường
└── README.md                   # Tài liệu dự án
```

## 8. Lưu ý bảo mật

- Không commit file `.env`, khóa riêng SSL hoặc các thông tin xác thực thật.
- Kiểm tra `.gitignore` trước khi thêm tệp vào Git.
- Thay mật khẩu mẫu trước khi khởi chạy.
- Chứng chỉ tự ký chỉ phù hợp cho mục đích thử nghiệm; triển khai thực tế cần cấu hình chứng chỉ phù hợp và giới hạn quyền truy cập các dịch vụ.
