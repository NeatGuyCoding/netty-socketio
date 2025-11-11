# Netty SocketIO Performance Test Report

This report contains daily performance test results for Netty SocketIO.

## Test Configuration
- Server Port: 8899
- Client Count: 10
- Messages per Client: 50000
- Message Size: 32 bytes
- Server Max Memory: 256 MB

## Test Results

*Results will be automatically updated daily by GitHub Actions*

---

## Historical Results

| Date | Java Version | OS | CPU Cores | Messages/sec | Avg Latency (ms) | P99 Latency (ms) | Error Rate (%) | Max Heap (MB) | JVM Args | Git Branch | Version | Test Duration (ms) |
|------|-------------|----|-----------|--------------|------------------|------------------|----------------|---------------|-----------|------------|---------|-------------------|
| 2025-11-11 07:25:31 | 11.0.29 | Linux 6.11.0-1018-azure | 4 | 181,028.24 | 1618.49 | 2239 | 0.0000 | 256 | -Xms256m -Xmx256m -XX:+UseG1GC -XX:+AlwaysPreTouch | v3.x | 3.0.1 | 2762 |
| 2025-10-16 00:48:47 | 25 | Linux 6.11.0-1018-azure | 4 | 224,618.15 | 1142.70 | 1743 | 0.0000 | 256 | -Xms256m -Xmx256m -XX:+UseG1GC -XX:+AlwaysPreTouch | v3.x | 3.0.0 | 2226 |
| 2025-10-16 00:45:19 | 21.0.8 | Linux 6.11.0-1018-azure | 4 | 206,270.63 | 1359.11 | 2007 | 0.0000 | 256 | -Xms256m -Xmx256m -XX:+UseG1GC -XX:+AlwaysPreTouch | v3.x | 3.0.0 | 2424 |
| 2025-10-16 00:29:15 | 17.0.16 | Linux 6.11.0-1018-azure | 4 | 194,476.86 | 1554.77 | 2191 | 0.0000 | 256 | -Xms256m -Xmx256m -XX:+UseG1GC -XX:+AlwaysPreTouch | v3.x | 3.0.0 | 2571 |
| 2025-10-16 00:27:04 | 11.0.28 | Linux 6.11.0-1018-azure | 4 | 186,567.16 | 1566.61 | 2255 | 0.0000 | 256 | -Xms256m -Xmx256m -XX:+UseG1GC -XX:+AlwaysPreTouch | v3.x | 3.0.0 | 2680 |
