import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllOrders.css';

function AllOrders() {
    // const [tables, setTables] = useState([]);
    // const [selectedTable, setSelectedTable] = useState(null);
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8084/api/order/getAllOrders')
            .then(response => setOrders(response.data))
            .catch(error => console.error('Error fetching tables', error));
    }, []);

    // const fetchOrders = (tableId) => {
    //     axios.get(`http://localhost:8084/api/order/getOrders/${tableId}`)
    //         .then(response => setOrders(response.data))
    //         .catch(error => console.error('Error fetching orders', error));
    // };

    // const handleTableClick = (tableId) => {
    //     setSelectedTable(tableId);
    //     fetchOrders(tableId);
    // };

    // const handleCheckout = (tableId) => {
    //     if (window.confirm('Are you sure you want to checkout and clear all orders for this table?')) {
    //         axios.delete(`http://localhost:8084/api/order/${tableId}`)
    //             .then(() => {
    //                 fetchOrders(tableId); // Refresh orders which should be empty now
    //                 alert('Checked out successfully.');
    //             })
    //             .catch(error => console.error('Error checking out', error));
    //     }
    // };

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
            <h4>Pending Orders</h4>
            
            <div className="orders-container" style={{ maxHeight: '70%', overflowY: 'scroll' }}>
            {orders.length > 0 ? (
                orders.map(order => (
                    <div key={order.id}>
                        <p>Order ID: {order.id}</p>
                        <p>Table No: {order.tableId}</p>
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
                <p>No pending orders</p>
            )}
                {/* {selectedTable && <button onClick={() => handleCheckout(selectedTable)}>Checkout</button>} */}
            </div>
        </div>
    );
}

export default AllOrders;