import DefaultNavbar from "./default_navbar";
import { Outlet } from 'react-router-dom';

export default function Layout() {
    return (
        <>
            <DefaultNavbar />
            {/* <aside></aside> */}
            <main className="container mx-auto">
                <Outlet />
            </main>
        </>
    );
}