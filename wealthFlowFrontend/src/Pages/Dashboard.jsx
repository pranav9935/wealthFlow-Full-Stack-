import { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const navigate = useNavigate();

  const [portfolio, setPortfolio] = useState(null);
  const [stockName, setStockName] = useState("");
  const [stockSymbol, setStockSymbol] = useState("");
 const [quantity, setQuantity] = useState(0);
const [buyPrice, setBuyPrice] = useState(0);

const fetchDashboard = async () => {

  const token = sessionStorage.getItem("token");

  if (!token) {
    navigate("/login", { replace: true });
    return;
  }

  try {

    const res = await api.get("/dashboard");

    setPortfolio(res.data);

  } catch (err) {

    if (err.response && err.response.status === 401) {

      sessionStorage.removeItem("token");

      navigate("/login", { replace: true });

    } else {

      console.log(err);

    }

  }

};

useEffect(() => {

  const token = sessionStorage.getItem("token");

  if (!token) {
    navigate("/login", { replace: true });
    return;
  }

  fetchDashboard();

  const handleBackButton = () => {

    sessionStorage.removeItem("token");

    navigate("/login", { replace: true });

  };

  window.addEventListener("popstate", handleBackButton);

  return () => {
    window.removeEventListener("popstate", handleBackButton);
  };

}, [navigate]);

  const handleAddInvestment = async () => {

    try {

      await api.post("/investments", {
        stockSymbol,
        stockName,
        quantity,
        buyPrice
      });

      setStockName("");
      setStockSymbol("");
      setQuantity("");
      setBuyPrice("");

      fetchDashboard();

    } catch (err) {

      alert("Error adding investment");

    }

  };

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

      {/* Add Investment */}

      <div className="add-investment">

        <h3>Add Investment</h3>

        <input
          type="text"
          placeholder="Stock Name (Apple Inc)"
          value={stockName}
          onChange={(e) => setStockName(e.target.value)}
        />

        <input
          type="text"
          placeholder="Stock Symbol (AAPL)"
          value={stockSymbol}
          onChange={(e) => setStockSymbol(e.target.value)}
        />

        <input
          type="number"
          placeholder="Quantity"
          value={quantity}
          onChange={(e) => setQuantity(Number(e.target.value))}
        />

        <input
          type="number"
          placeholder="Buy Price"
          value={buyPrice}
          onChange={(e) => setBuyPrice(Number(e.target.value))}
        />

        <button onClick={handleAddInvestment}>
          Add Investment
        </button>

      </div>

      {/* Investments Table */}

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

{portfolio.investments.map((stock, index) => (

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