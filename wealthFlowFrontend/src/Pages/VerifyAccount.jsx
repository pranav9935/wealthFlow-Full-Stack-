import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function VerifyAccount() {

  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleSendOtp = async (e) => {

    e.preventDefault();

    try {

      await api.post("/auth/request-verification", { email });

      toast.success("OTP sent to your email");

      navigate("/verify-otp", { state: { email } });

    } catch (err) {

      toast.error(err.response?.data || "Account does not exist");

    }

  };

  return (

    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">Verify Account</h1>

        <p className="subtitle">
          Enter your email to receive OTP
        </p>

        <form onSubmit={handleSendOtp}>

          <input
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <button type="submit">
            Send OTP
          </button>

        </form>

      </div>

    </div>

  );
}

export default VerifyAccount;