import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {

  e.preventDefault();

  try {

    const res = await api.post("/auth/login", {
      email,
      password
    });

    localStorage.setItem("token", res.data);

    toast.success("Login successful");

    setTimeout(() => {
      navigate("/dashboard");
    }, 1000);

  } catch (err) {

    toast.error("Invalid email or password");

  }

};
  return (

    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">WealthFlow</h1>

        <p className="subtitle">
          Smart Investment Portfolio Manager
        </p>

        <form onSubmit={handleLogin}>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit">
            Login
          </button>

        </form>

        <div className="divider"></div>

        <p className="register-text">
          Don't have an account?
        </p>

        <button
          className="register-btn"
          onClick={() => navigate("/register")}
        >
          Create Account
        </button>

      </div>

    </div>

  );
}

export default Login;