# 🌳 Pachira Aquatica

**Real-time Quantitative Trading Data Platform**

Pachira-aquatica is a SpringBoot-based application that displays real-time quantitative trading data from backend servers on PC web pages, WeChat Mini Programs, and apps.

![Pachira Aquatica UI](https://github.com/user-attachments/assets/d2fbe787-33d7-45e2-9663-a7850446f78b)

## Features

- ✅ **Real-time Trading Data**: WebSocket-based real-time market data updates every 2 seconds
- ✅ **REST API**: Comprehensive REST API for accessing trading data
- ✅ **Multi-platform Support**: CORS-enabled for PC web, WeChat Mini Programs, and mobile apps
- ✅ **Market Summary**: Overview of market statistics (advancers, decliners, total volume)
- ✅ **Symbol-specific Data**: Individual stock/symbol trading data with price, volume, high/low
- ✅ **Responsive UI**: Modern web interface with real-time updates

## Technology Stack

- **Backend**: Spring Boot 3.1.5
- **Build Tool**: Maven
- **Java Version**: 17
- **WebSocket**: STOMP over SockJS
- **Frontend**: HTML5, CSS3, JavaScript

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/kangaxx/Pachira-aquatica.git
   cd Pachira-aquatica
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Web UI: http://localhost:8080
   - REST API: http://localhost:8080/api/v1/trading/

## API Documentation

### REST Endpoints

#### Health Check
```
GET /api/v1/trading/health
```
Returns the health status of the trading service.

**Response:**
```
Pachira Aquatica Trading Service is running
```

#### Get All Trading Data
```
GET /api/v1/trading/data
```
Returns all available trading symbols with their current data.

**Response:**
```json
[
  {
    "symbol": "AAPL",
    "price": 150.25,
    "volume": 1000000,
    "high": 155.00,
    "low": 148.00,
    "open": 150.00,
    "close": 150.00,
    "timestamp": "2025-11-06T01:30:00",
    "exchange": "NASDAQ",
    "changePercent": 0.17
  }
]
```

#### Get Trading Data by Symbol
```
GET /api/v1/trading/data/{symbol}
```
Returns trading data for a specific symbol.

**Example:**
```bash
curl http://localhost:8080/api/v1/trading/data/AAPL
```

#### Get Market Summary
```
GET /api/v1/trading/summary
```
Returns overall market statistics.

**Response:**
```json
{
  "totalSymbols": 10,
  "advancers": 5,
  "decliners": 4,
  "unchanged": 1,
  "totalVolume": 5000000,
  "timestamp": "2025-11-06T01:30:00"
}
```

### WebSocket Endpoints

#### Connection Endpoint
```
ws://localhost:8080/ws-trading
```

#### Subscribe to Topics

- **Market Summary**: `/topic/market-summary`
- **Individual Symbol**: `/topic/trading/{symbol}`

**Example JavaScript:**
```javascript
const socket = new SockJS('/ws-trading');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    // Subscribe to all updates
    stompClient.subscribe('/topic/trading/AAPL', function(message) {
        const data = JSON.parse(message.body);
        console.log('AAPL price:', data.price);
    });
});
```

## Project Structure

```
src/
├── main/
│   ├── java/com/pachira/aquatica/
│   │   ├── PachiraAquaticaApplication.java      # Main application class
│   │   ├── config/
│   │   │   ├── CorsConfig.java                  # CORS configuration
│   │   │   └── WebSocketConfig.java             # WebSocket configuration
│   │   ├── controller/
│   │   │   └── TradingDataController.java       # REST API endpoints
│   │   ├── model/
│   │   │   ├── TradingData.java                 # Trading data model
│   │   │   └── MarketSummary.java               # Market summary model
│   │   └── service/
│   │       └── TradingDataService.java          # Business logic
│   └── resources/
│       ├── application.properties               # Application configuration
│       └── static/
│           └── index.html                       # Web UI
└── test/
    └── java/com/pachira/aquatica/
        ├── PachiraAquaticaApplicationTests.java
        ├── controller/
        │   └── TradingDataControllerTest.java
        └── service/
            └── TradingDataServiceTest.java
```

## Configuration

The application can be configured via `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Logging level
logging.level.root=INFO
logging.level.com.pachira.aquatica=DEBUG
```

## Testing

Run the test suite:
```bash
mvn test
```

## Supported Trading Symbols

The application currently simulates data for the following symbols:
- AAPL (Apple)
- GOOGL (Google)
- MSFT (Microsoft)
- AMZN (Amazon)
- TSLA (Tesla)
- META (Meta)
- NVDA (NVIDIA)
- JPM (JPMorgan Chase)
- V (Visa)
- WMT (Walmart)

## Cross-Platform Support

### PC Web Browsers
Access directly at `http://localhost:8080`

### WeChat Mini Programs
Use the REST API endpoints with CORS enabled for cross-origin requests.

### Mobile Apps
Integrate using the REST API or WebSocket endpoints. CORS is configured to accept requests from any origin.

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues and questions, please open an issue on the GitHub repository.
