import './default_navbar.css';
import menusvg from '../../assets/menu.svg';
import { Link } from 'react-router-dom';

export default function DefaultNavbar() {
    return (
        <header>
            <nav>
                <Link to="/" className="logo">
                    <img src="https://flowbite.com/docs/images/logo.svg" alt="Flowbite Logo" />
                    <span>Store</span>
                </Link>
                <button data-collapse-toggle="navbar-default" type="button" className="menu-btn" aria-controls="navbar-default" aria-expanded="false">
                    <span className="sr-only">Open main menu</span>
                    <img src={menusvg} alt="Menu logo" />
                </button>
                <div className="menu hidden" id="navbar-default">
                    <ul className="menu">
                        <li>
                            <Link to="/" className="current" aria-current="page">Home</Link>
                        </li>
                        <li>
                            <Link to="/products" aria-current="page">Products panel</Link>
                        </li>
                        <li>
                            <Link to="/categories" aria-current="page">Categories panel</Link>
                        </li>
                        <li>
                            <Link to="/products/create">Create a product</Link>
                        </li>
                        <li>
                            <Link to="/categories/create">Create a category</Link>
                        </li>
                        <li>
                            <Link to="#">About</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    );
}