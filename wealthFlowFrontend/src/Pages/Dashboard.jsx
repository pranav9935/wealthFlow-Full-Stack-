import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { Client } from "@stomp/stompjs";
import { toast } from "react-toastify";

function Dashboard() {

  const navigate = useNavigate();

  const [portfolio, setPortfolio] = useState(null);

  const [mode, setMode] = useState("buy");

  const [stocks, setStocks] = useState([]);
  const [search, setSearch] = useState("");
  const [selectedStock, setSelectedStock] = useState(null);

  const [stockName, setStockName] = useState("");
  const [stockSymbol, setStockSymbol] = useState("");

  const [quantity, setQuantity] = useState("");
  const [sellQty, setSellQty] = useState(0);

  const [price, setPrice] = useState(0);

  /* ---------- FETCH DASHBOARD ---------- */

  const fetchDashboard = async () => {

    try {

      const res = await api.get("/dashboard");
      setPortfolio(res.data);

    } catch (err) {

      if (err.response?.status === 401) {

        sessionStorage.removeItem("token");
        navigate("/login", { replace: true });

      }

    }

  };

  /* ---------- AUTH CHECK ---------- */

  useEffect(() => {

    const token = sessionStorage.getItem("token");

    if (!token) {
      navigate("/login", { replace: true });
      return;
    }

    fetchDashboard();

  }, [navigate]);

  /* ---------- WEBSOCKET ---------- */

  useEffect(() => {

    const token = sessionStorage.getItem("token");
    const email = JSON.parse(atob(token.split(".")[1])).sub;

    const client = new Client({
      brokerURL: "wss://wealthflow-full-stack.onrender.com/ws",
      reconnectDelay: 5000
    });

    client.onConnect = () => {

      client.subscribe(`/topic/portfolio/${email}`, (message) => {

        const data = JSON.parse(message.body);
        setPortfolio(data);

      });

    };

    client.activate();

    return () => client.deactivate();

  }, []);

  /* ---------- STOCK SEARCH ---------- */

  const searchStocks = async (value) => {

    setSearch(value);

    if (value.length < 2) return;

    try {

      const res = await api.get(`/stocks/search?query=${value}`);
      setStocks(res.data);

    } catch (err) {

      console.log(err);

    }

  };

  /* ---------- SELECT STOCK ---------- */

const selectStock = async (stock) => {

  setSelectedStock(stock);

  setStockSymbol(stock.symbol);
  setStockName(stock.description);

  // close dropdown
  setStocks([]);
  setSearch(`${stock.description} (${stock.symbol})`);

  try {

    const res = await fetch(
      `https://finnhub.io/api/v1/quote?symbol=${stock.symbol}&token=d6jtmg9r01qkvh5rausgd6jtmg9r01qkvh5raut0`
    );

    const data = await res.json();

    setPrice(data.c);

  } catch (err) {
    console.log(err);
  }

};
  /* ---------- BUY STOCK ---------- */

  const handleBuy = async () => {

  if (!selectedStock) {
    toast.error("Please select a stock to buy");
    return;
  }

  if (!quantity || quantity <= 0) {
    toast.error("Enter a valid quantity");
    return;
  }

  try {

    await api.post("/investments/buy", {
      stockSymbol: selectedStock.symbol,
      stockName: selectedStock.description,
      quantity: quantity,
      buyPrice: price
    });

    toast.success("Stock purchased successfully");

    fetchDashboard();

    setQuantity(0);
    setSelectedStock(null);
    setSearch("");

  } catch (err) {

    toast.error(err.response?.data || "Buy failed");

  }

};
  /* ---------- SELL STOCK ---------- */

  const handleSell = async () => {

  if (!selectedStock) {
    toast.error("Please select a stock to sell");
    return;
  }

  const ownedStock = portfolio.investments.find(
    (s) => s.stockSymbol === selectedStock.symbol
  );

  if (!ownedStock) {
    toast.error("You don't own this stock");
    return;
  }

  if (sellQty <= 0) {
    toast.error("Enter a valid quantity");
    return;
  }

  if (sellQty > ownedStock.quantity) {
    toast.error("Invalid quantity. Not enough stocks to sell.");
    return;
  }

  try {

    await api.post("/investments/sell", {
      stockSymbol: selectedStock.symbol,
      quantity: sellQty
    });

    toast.success("Stock sold successfully");

    fetchDashboard();

    setSellQty(0);
    setSelectedStock(null);
    setSearch("");

  } catch (err) {

    toast.error(err.response?.data || "Sell failed");

  }

};

  /* ---------- LOGOUT ---------- */

  const handleLogout = () => {

    sessionStorage.removeItem("token");
    navigate("/login", { replace: true });

  };

  if (!portfolio) {

    return (
      <div className="loading-container">
        <div className="loader"></div>
      </div>
    );

  }

  return (

    <div className="dashboard-container">

      <div className="dashboard-header">

        <h2>Investment Portfolio</h2>

        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>

      </div>

      {/* Portfolio Cards */}

      <div className="portfolio-cards">

        <div className="card">
          <h4>Total Investment</h4>
          <p>${portfolio.totalInvestment}</p>
        </div>

        <div className="card">
          <h4>Current Value</h4>
          <p>${portfolio.currentValue}</p>
        </div>

        <div className="card">
          <h4>Profit / Loss</h4>
          <p className={portfolio.profitLoss >= 0 ? "profit" : "loss"}>
            ${portfolio.profitLoss}
          </p>
        </div>

      </div>

      {/* BUY / SELL */}

     <div className="add-investment">

  <h3>
  {mode === "buy" ? "Buy Stock" : mode === "sell" ? "Sell Stock" : "Trade Stock"}
</h3>

  <div className="trade-toggle">

    <button
      className={mode === "buy" ? "buy-active" : "trade-btn"}
      onClick={() => setMode("buy")}
    >
      Buy
    </button>

    <button
      className={mode === "sell" ? "sell-active" : "trade-btn"}
      onClick={() => setMode("sell")}
    >
      Sell
    </button>

  </div>

        <input
          type="text"
          placeholder="Search Stock"
          value={search}
          onChange={(e) => searchStocks(e.target.value)}
        />

        {stocks.map((stock,index)=>(
          <div
            key={index}
            style={{cursor:"pointer"}}
            onClick={()=>selectStock(stock)}
          >
            {stock.description} ({stock.symbol})
          </div>
        ))}

        {selectedStock && (

          <>
            <p>Current Price: ${price}</p>

            {mode === "buy" && (

              <>
                          <input
              type="number"
              placeholder="Buy Quantity"
              value={quantity}
              onChange={(e)=>setQuantity(e.target.value)}
            />

                <p>Total: ${(Number(quantity || 0) * price).toFixed(2)}</p>

                <button onClick={handleBuy}>
                  Buy Stock
                </button>
              </>

            )}

            {mode === "sell" && (

              <>
                <input
                  type="number"
                  placeholder="Sell Quantity"
                  value={sellQty}
                  onChange={(e)=>setSellQty(Number(e.target.value))}
                />

                <button onClick={handleSell}>
                  Sell Stock
                </button>
              </>

            )}

          </>

        )}

      </div>

      {/* INVESTMENT TABLE */}

      <div className="investment-table">

        <h3>Your Investments</h3>

        <table>

          <thead>
            <tr>
              <th>Stock Name</th>
              <th>Symbol</th>
              <th>Quantity</th>
              <th>Avg Buy Price</th>
              <th>Current Price</th>
              <th>Current Value</th>
            </tr>
          </thead>

          <tbody>

            {portfolio.investments.map((stock,index)=>(

              <tr key={index}>
                <td>{stock.stockName}</td>
                <td>{stock.stockSymbol}</td>
                <td>{stock.quantity}</td>
                <td>${stock.buyPrice.toFixed(2)}</td>
                <td>${stock.currentPrice.toFixed(2)}</td>
                <td className={stock.profit >= 0 ? "profit" : "loss"}>
                  ${stock.currentValue.toFixed(2)}
                </td>
              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </div>

  );

}

export default Dashboard;