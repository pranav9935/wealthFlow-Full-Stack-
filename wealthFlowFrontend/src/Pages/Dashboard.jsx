import { useEffect, useState } from "react";
import api from "../api/axios";

function Dashboard() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    api.get("/users/me")
      .then((res) => setUser(res.data))
      .catch(() => alert("Unauthorized. Please login again."));
  }, []);

  if (!user) return <div className="dashboard">Loading...</div>;

  return (
    <div className="dashboard">
      <h2>Dashboard</h2>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Role:</strong> {user.role}</p>
    </div>
  );
}

export default Dashboard;