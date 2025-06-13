import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Admin.css';
import { useNavigate } from 'react-router-dom';

function Admin() {
    const navigate = useNavigate();
    const [tables, setTables] = useState([]);
    const [selectedTable, setSelectedTable] = useState(null);
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8084/api/admin/tables')
            .then(response => setTables(response.data))
            .catch(error => console.error('Error fetching tables', error));
    }, []);

    const fetchOrders = (tableId) => {
        axios.get(`http://localhost:8084/api/order/getOrders/${tableId}`)
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
                const response = await axios.post(`http://localhost:8084/api/receipt/${tableId}/checkout`);
                const receipt = response.data;
                console.log("Receipt Data:", receipt);
console.log("Navigating to ID:", receipt.id);
                navigate(`/receipt/${receipt.id}`, { state: { receipt: receipt } });
                // Temporarily mock the receipt data to test navigation
                // const mockReceipt = {
                //     id: '123456',
                //     itemsTotal: 100.0,
                //     tax: 20.0,
                //     grandTotal: 120.0,
                //     receiptItemDTOList: [
                //         { id: '1', menuItemName: 'Pizza', quantity: 2 },
                //         { id: '2', menuItemName: 'Burger', quantity: 3 }
                //     ]
                // };
                // navigate(`/receipt/${mockReceipt.id}`, { state: { receipt: mockReceipt } });
            } catch (error) {
                console.error('Error during checkout', error);
            }
        }
    };

    const handleToggleServed = async (order) => {
        if (!order.done) {
            try {
                // Assuming the API sets the order to "done" and returns the updated order
                const response = await axios.post(`http://localhost:8084/api/order/${order.id}/isDone`);
                if (response.status === 200) {
                    const updatedOrder = response.data;

                    // Update orders state
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

    return (
        <div>
            <h2>Admin Dashboard</h2>
            <div className="tables-grid">
                {tables.map(table => (
                    <button key={table} onClick={() => handleTableClick(table)}>
                        Table ID: {table}
                    </button>
                ))}
            </div>
            <div className="orders-container" style={{ maxHeight: '70%', overflowY: 'scroll' }}>
                {selectedTable && orders.length > 0 ? (
                    orders.map(order => (
                        <div key={order.id}>
                            <p>Order ID: {order.id}</p>
                            <div>
                                {/* <h2>Table {order.tableId}</h2> */}
                                <ul>
                                    {order.orderItems.map(item => (
                                        <li key={item.id}>{item.menuItemName} x {item.quantity}</li>
                                    ))}
                                </ul>
                            </div>
                            {/* <button className="served-button" onClick={() => handleServed(order.id)}>✔ Served</button> */}
                            <p>Served:
                                <input
                                    type="checkbox"
                                    checked={order.done}
                                    onChange={() => handleToggleServed(order)}
                                    disabled={order.done} // Disables checkbox if already served
                                />
                            </p>
                        </div>

                    ))


                ) : (
                    <p>No orders</p>
                )}
                {selectedTable && orders.length > 0 && <button onClick={() => handleCheckout(selectedTable)}>Checkout</button>}
            </div>
            {/*             
            <div className="orders-container" style={{ maxHeight: '70%', overflowY: 'scroll' }}>
                {selectedTable && orders.length > 0 ? (
                    orders.map(order => (
                        <div key={order.id}>
                            <p>Order ID: {order.id}</p>
                            <ul>
                                {order.orderItems.map(item => (
                                    <li key={item.id}>{item.menuItemName} x {item.quantity}</li>
                                ))}
                            </ul>
                            <button onClick={() => handleCheckout(selectedTable)}>Checkout</button>
                        </div>
                    ))
                ) : (
                    <p>No orders</p>
                )}
            </div> */}
        </div>
    );
}

export default Admin;




