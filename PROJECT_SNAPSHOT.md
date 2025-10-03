markdown
# 股票回溯策略系统 - 项目快照

## 项目概述
- **项目类型**: Spring Boot + Vue.js 全栈项目
- **主要功能**: 三市场股票指数K线图展示 + 资金增长曲线
- **技术栈**: 
  - 后端: Spring Boot 2.7.0, Java 11, Maven
  - 前端: Vue 3, TypeScript, Element Plus, ECharts
  - 构建工具: Maven, Vite

## 项目结构
stock-backtest-system/
├── backend/ # Spring Boot后端
│ ├── src/main/java/com/example/stockbacktest/
│ │ ├── controller/ # 控制器
│ │ │ ├── KlineController.java
│ │ │ ├── FileUploadController.java
│ │ │ └── ChartDataController.java
│ │ ├── service/ # 服务层
│ │ │ ├── TushareService.java
│ │ │ └── DataParseService.java
│ │ ├── model/ # 数据模型
│ │ │ ├── StockData.java
│ │ │ └── MoneyGrowData.java
│ │ └── StockBacktestApplication.java
│ └── pom.xml
├── frontend/ # Vue前端
│ ├── src/
│ │ ├── views/ # 页面组件
│ │ │ ├── ChinaMarket.vue
│ │ │ ├── USMarket.vue
│ │ │ └── HKMarket.vue
│ │ ├── components/ # 可复用组件
│ │ │ ├── MarketChart.vue
│ │ │ └── FileUpload.vue
│ │ ├── stores/ # 状态管理
│ │ │ └── marketStore.ts
│ │ ├── services/ # API服务
│ │ │ └── api.ts
│ │ └── router/ # 路由配置
│ │ └── index.ts
│ └── package.json
└── PROJECT_SNAPSHOT.md # 本文件

