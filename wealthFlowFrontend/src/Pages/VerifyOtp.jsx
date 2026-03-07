import { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate, useLocation } from "react-router-dom";
import { toast } from "react-toastify";

function VerifyOtp() {

  const navigate = useNavigate();
  const location = useLocation();

  const email = location.state?.email;

  const [otp, setOtp] = useState("");

  // Redirect safely if email not present
  useEffect(() => {
    if (!email) {
      navigate("/register", { replace: true });
    }
  }, [email, navigate]);

  const handleVerify = async (e) => {

    e.preventDefault();

    try {

      await api.post("/auth/verify-otp", {
        email,
        otp
      });

      toast.success("Email verified successfully");

      setTimeout(() => {
        navigate("/login");
      }, 1000);

    } catch (err) {

      toast.error(err.response?.data || "Invalid or expired OTP");

    }

  };

  const handleResend = async () => {

    try {

      await api.post("/auth/resend-otp", {
        email
      });

      toast.success("New OTP sent to email");

    } catch (err) {

      toast.error(err.response?.data || "Failed to resend OTP");

    }

  };

  return (

    <div className="login-container">

      <div className="login-card">

        <h1 className="logo">Verify Email</h1>

        <p className="subtitle">
          Enter the OTP sent to your email
        </p>

        <form onSubmit={handleVerify}>

          <input
            type="text"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            required
          />

          <button type="submit">
            Verify OTP
          </button>

        </form>

        <div className="divider"></div>

        <button
          className="register-btn"
          onClick={handleResend}
        >
          Resend OTP
        </button>

      </div>

    </div>

  );
}

export default VerifyOtp;