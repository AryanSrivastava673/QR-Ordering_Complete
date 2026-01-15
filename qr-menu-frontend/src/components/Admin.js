import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Admin.css';
import AdminLogin from './AdminLogin';
import { useNavigate } from 'react-router-dom';


function Admin() {
    const navigate = useNavigate();
    const [tables, setTables] = useState([]);
    const [selectedTable, setSelectedTable] = useState(null);
    const [orders, setOrders] = useState([]);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [checkingAuth, setCheckingAuth] = useState(true);


    // Check admin session on mount
    useEffect(() => {
        axios.get('http://localhost:8084/api/admin/session', { withCredentials: true })
            .then(res => {
                if (res.data && res.data.authenticated) {
                    setIsAuthenticated(true);
                } else {
                    setIsAuthenticated(false);
                }
                setCheckingAuth(false);
            })
            .catch(() => {
                setIsAuthenticated(false);
                setCheckingAuth(false);
            });
    }, []);

    useEffect(() => {
        if (isAuthenticated) {
            axios.get('http://localhost:8084/api/admin/tables', { withCredentials: true })
                .then(response => setTables(response.data))
                .catch(error => console.error('Error fetching tables', error));
        }
    }, [isAuthenticated]);
    // Handle admin login
    const handleAdminLogin = async (credentials, setError) => {
        try {
            const response = await axios.post('http://localhost:8084/api/admin/login', credentials, { withCredentials: true });
            if (response.status === 200 && response.data.success) {
                setIsAuthenticated(true);
            } else {
                setError('Invalid username or password.');
            }
        } catch (err) {
            setError('Login failed. Please try again.');
        }
    };

    const handleLogout = async () => {
        await axios.post('http://localhost:8084/api/admin/logout', {}, { withCredentials: true });
        setIsAuthenticated(false);
    };

    const fetchOrders = (tableId) => {
        axios.get(`http://localhost:8084/api/order/getOrders/${tableId}`, { withCredentials: true })
            .then(response => setOrders(response.data))
            .catch(error => console.error('Error fetching orders', error));
    };

    const handleTableClick = (tableId) => {
        setSelectedTable(tableId);
        fetchOrders(tableId);
    };

    const handleCheckout = async (tableId) => {
        if (window.confirm('Are you sure you want to checkout all orders for this table and clear them?')) {
            try {
                const response = await axios.post(`http://localhost:8084/api/receipt/${tableId}/checkout`, {}, { withCredentials: true });
                const receipt = response.data;
                navigate(`/receipt/${receipt.id}`, { state: { receipt: receipt } });
            } catch (error) {
                console.error('Error during checkout', error);
            }
        }
    };

    const handleToggleServed = async (order) => {
        if (!order.done) {
            try {
                const response = await axios.post(`http://localhost:8084/api/order/${order.id}/isDone`, {}, { withCredentials: true });
                if (response.status === 200) {
                    const updatedOrder = response.data;
                    const updatedOrders = orders.map(ord =>
                        ord.id === order.id ? { ...ord, done: updatedOrder.done } : ord
                    );
                    setOrders(updatedOrders);
                }
            } catch (error) {
                console.error('Failed to update order status', error);
            }
        }
    };

    if (checkingAuth) {
        return <div>Loading...</div>;
    }
    return (
        <div>
            {!isAuthenticated ? (
                <AdminLogin onLogin={handleAdminLogin} />
            ) : (
                <>
                    <div className="admin-header">
                        <h2>Admin Dashboard</h2>
                        <button onClick={handleLogout}>Logout</button>
                    </div>
                    <div className="admin-main-layout">
                        <div className="admin-tables-panel">
                            <div className="tables-grid">
                                {tables.map(table => (
                                    <button
                                        key={table}
                                        className={selectedTable === table ? 'selected' : ''}
                                        onClick={() => handleTableClick(table)}
                                    >
                                        Table ID: {table}
                                    </button>
                                ))}
                            </div>
                        </div>
                        <div className="admin-orders-panel">
                            <div className="orders-container">
                                {selectedTable && orders.length > 0 ? (
                                    <div className="orders-grid">
                                        {orders.map(order => (
                                            <div key={order.id} className="order-card">
                                                <p>Order ID: {order.id}</p>
                                                <div>
                                                    <ul>
                                                        {order.orderItems.map(item => (
                                                            <li key={item.id}>{item.menuItemName} x {item.quantity}</li>
                                                        ))}
                                                    </ul>
                                                </div>
                                                <p>Served:
                                                    <input
                                                        type="checkbox"
                                                        checked={order.done}
                                                        onChange={() => handleToggleServed(order)}
                                                        disabled={order.done}
                                                    />
                                                </p>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <p>No orders</p>
                                )}
                                {selectedTable && orders.length > 0 && (
                                    <button onClick={() => handleCheckout(selectedTable)}>
                                        Checkout
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}

export default Admin;




