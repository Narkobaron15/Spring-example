import './default_navbar.css';
import menusvg from '../../assets/menu.svg';

export default function DefaultNavbar() {
    return (
        <header>
            <nav>
                <a href="https://flowbite.com/" className="logo">
                    <img src="https://flowbite.com/docs/images/logo.svg" alt="Flowbite Logo" />
                    <span>Flowbite</span>
                </a>
                <button data-collapse-toggle="navbar-default" type="button" className="menu-btn" aria-controls="navbar-default" aria-expanded="false">
                    <span className="sr-only">Open main menu</span>
                    <img src={menusvg} alt="" />
                </button>
                <div className="menu hidden" id="navbar-default">
                    <ul className="menu">
                        <li>
                            <a href="#" className="current" aria-current="page">Home</a>
                        </li>
                        <li>
                            <a href="#">About</a>
                        </li>
                        <li>
                            <a href="#">Services</a>
                        </li>
                        <li>
                            <a href="#">Pricing</a>
                        </li>
                        <li>
                            <a href="#">Contact</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    );
}