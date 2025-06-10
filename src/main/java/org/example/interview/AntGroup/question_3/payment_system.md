# Simple payment system
## Functional Requirements:
- Users can pay for orders (e.g. e-wallet, bank card, or payment gateway like Stripe/MoMo).
- Verify and record transaction results (success/failure).
- Admin can retrieve transactions.

## Non-Functional Requirements:
- High reliability: no loss of money, 100% correct processing.

- Idempotency: API calls do not cause duplicate transactions.

- Security: sensitive information (card number, token).

- Audit: full traceability.

- Scalable: process thousands of payments/minute.


## Detailed ingredients
1. Payment API:
    - Process payment creation request: POST /payments.
    - Validate input information: order_id, amount, method.
    - Create PENDING transaction in DB.
    - Generate idempotency key (to avoid duplication if retrying).

2. Payment Core Service:
    - Connect to payment gateway (Stripe, VNPay...) by payment method.
    - Sign data, encrypt if needed.
    - Switch to PROCESSING state.

3. External Payment Gateway:
    - Payment gateway processes and redirects users.
    - After payment, call back system webhook with result (SUCCESS, FAILED).

Sau khi thanh toán, gọi lại webhook của hệ thống với kết quả (SUCCESS, FAILED)x

4. Webhook Endpoint:
    - Verify signature from payment gateway.
    - Update order status, transaction → SUCCESS / FAILED.
    - Send signal to Notification Service.


5. Notification Service:
    - Send email, SMS or push notification.
    - Logging for audit.

6. Transaction DB:
    - Tables:
        - transactions: save transaction information.
        - orders: linked to orders.
        - idempotency keys: cache requests to avoid reprocessing.

## Security & Idempotency:
| Problem                   |Solution                                                           |
| --------------------------|-----------------------------------------------------------------  |
| Duplicate payment         | Use unique `idempotency key` for each request                     |
| Webhook spoofing          | Verify signature or token signed with secret from payment gateway |
| Fake user                 | Authentication with OAuth2 / JWT                                  |
| Information disclosure    | Do not store card numbers, tokenize data                          |

## Flow tổng quát
1. User clicks "Payment" → calls POST /payments.
2. API checks idempotency key → creates PENDING transaction.
3. Redirect user to payment gateway (or call API with token).
4. Transaction successful → gateway calls system webhook.
5. Webhook update DB, send notification.
6. User is redirected to the results page.

## Công nghệ đề xuất

| Component       | Technologies                                   |
| --------------- | ---------------------------------------------- |
| Backend API     | Spring Boot / Node.js / FastAPI                |
| Payment gateways| Momo, VNPay, PayPal                            |
| DB              | PostgreSQL / MySQL                             |
| Message Queue   | Kafka / RabbitMQ                               |
| Caching         | Redis                                          |
| Auth            | OAuth2, JWT                                    |