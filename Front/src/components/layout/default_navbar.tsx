import React from 'react';

import './default_navbar.css';
import menusvg from '../../assets/menu.svg';
import { Link, useLocation } from 'react-router-dom';

export default function DefaultNavbar() {
    const location = useLocation();
    const [url, setUrl] = React.useState("");

    React.useEffect(() => {
        setUrl(location.pathname);
    }, [location]);

    return (
        <>
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
                            <Link to="/" className={getLinkStatus("/", url)}>Home</Link>
                        </li>
                        <li>
                            <Link to="/products" className={getLinkStatus("/products", url)}>Prods panel</Link>
                        </li>
                        <li>
                            <Link to="/categories" className={getLinkStatus("/categories", url)}>Cats panel</Link>
                        </li>
                        <li>
                            <Link to="/products/create" className={getLinkStatus("/products/create", url)}>Create a product</Link>
                        </li>
                        <li>
                            <Link to="/categories/create" className={getLinkStatus("/categories/create", url)}>Create a category</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </>
    );
}

function getLinkStatus(
    location: string,
    currentLocation: string,
    currentStatus: string = "current",
    defaultStatus: string = ""
): string {
    return location === currentLocation ? currentStatus : defaultStatus;
}