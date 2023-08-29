import { Link } from 'react-router-dom';
import './notfound.css';

const NotFound = () => {
  return (
    <div className="error-bg">
      <div className="error-box">
        <h1>404 Not Found</h1>
        <p>The page you're looking for does not exist.</p>
        <Link to="/">Go back to home</Link>
      </div>
    </div>
  );
};

export default NotFound;
