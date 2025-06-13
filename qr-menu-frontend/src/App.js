// import React from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import { TableProvider } from './context/TableContext';
// import { NotificationProvider } from './context/NotificationContext';
// import Navbar from './components/Navbar';
// import Menu from './components/Menu';
// import AboutSection from './components/AboutSection';
import './App.css';

// function App() {
//     return (
//         <TableProvider>
//           <NotificationProvider>
//             <Router>
//                 <div className="App">
//                     <Navbar />
//                     {/* <AboutSection /> */}
//                     <Routes>
//                         <Route path="/" element={<Menu />} />
//                     </Routes> 
//                 </div>
//             </Router>
//             </NotificationProvider>
//         </TableProvider>
//     );
// }

// export default App;

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { TableProvider } from './context/TableContext';
import { NotificationProvider } from './context/NotificationContext';
import Navbar from './components/Navbar';
import Menu from './components/Menu';
import TableRouterContextUpdater from './context/TableRouterContextUpdater';
import Admin from './components/Admin';
import Receipt from './components/Receipt';
import AllOrders from './components/AllOrders';

function App() {
    return (
        <TableProvider>
            <NotificationProvider>
                <Router>
                    <TableRouterContextUpdater />
                    <div className="App">
                        <Navbar />
                        <Routes>
                            <Route path="/admin" element={<Admin />} />
                            <Route path="/" element={<Menu />} />
                            <Route path="/:tableId" element={<Menu />} />
                            <Route path="/receipt/:receiptId" element={<Receipt />} />
                            <Route path="/Admin/all-orders" element={<AllOrders />} />
                        </Routes> 
                    </div>
                </Router>
            </NotificationProvider>
        </TableProvider>
    );
}

export default App;