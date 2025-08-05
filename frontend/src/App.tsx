import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Header from './Header';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <div className="main-content">
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
