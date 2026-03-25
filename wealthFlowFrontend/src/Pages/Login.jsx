import { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  useEffect(() => {

    const token = sessionStorage.getItem("token");

    if (token) {
      navigate("/dashboard", { replace: true });
    }

  }, [navigate]);

  const handleLogin = async (e) => {

    e.preventDefault();

    try {

      const res = await api.post("/auth/login", {
        email,
        password
      });

      sessionStorage.setItem("token", res.data);

      toast.success("Login successful");

      setTimeout(() => {
        navigate("/dashboard", { replace: true });
      }, 1000);

    } catch (err) {

      if (err.response && err.response.data) {
        toast.error(err.response.data);
      } else {
        toast.error("Login failed. Please try again.");
      }

    }

  };

  return (

    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">WealthFlow</h1>

        <p className="subtitle">
          Smart Investment Portfolio Manager
        </p>
        <p className="info-text">
  Initial load may take up to 60s (free hosting cold start).
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

        {/* Demo Login */}

        <div className="demo-box">

          <p className="demo-title">Demo Login</p>

          <p>Email: <b>pranavmishra9807@gmail.com</b></p>
          <p>Password: <b>123456</b></p>

        </div>

        {/* Testing Note */}

        <p className="test-note">
          OTP emails for account creation and verification are routed to
          <b> pranavmishra9807@gmail.com </b> due to domain verification limits.
        </p>

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

        <p className="verify-text">
          Haven't verified your account?{" "}
          <span
            className="verify-link"
            onClick={() => navigate("/verify-account")}
          >
            Verify
          </span>
        </p>

      </div>

    </div>

  );
}

export default Login;