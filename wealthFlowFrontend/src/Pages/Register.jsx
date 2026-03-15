import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function Register() {

  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

 const handleRegister = async (e) => {
  e.preventDefault();

  try {

    await api.post("/auth/register", {
      name,
      email,
      password
    });

    toast.success("Account created successfully");

    // SAVE EMAIL FOR OTP PAGE
    localStorage.setItem("verifyEmail", email);

    setTimeout(() => {
      navigate("/verify-otp");
    }, 1200);

  } catch (err) {

    if (err.response && err.response.data) {

      if (err.response.data.includes("Email already exists")) {
        toast.error("This email is already registered");
      } else {
        toast.error(err.response.data);
      }

    } else {
      toast.error("Registration failed. Please try again.");
    }

  }
};

  return (

    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">Create Account</h1>

        <p className="subtitle">
          Start managing your investments
        </p>

        <form onSubmit={handleRegister}>

          <input
            type="text"
            placeholder="Full Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />

          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit">
            Register
          </button>

        </form>

        <div className="divider"></div>

        <p className="register-text">
          Already have an account?
        </p>

        <button
          className="register-btn"
          onClick={() => navigate("/")}
        >
          Back to Login
        </button>

      </div>

    </div>

  );

}

export default Register;