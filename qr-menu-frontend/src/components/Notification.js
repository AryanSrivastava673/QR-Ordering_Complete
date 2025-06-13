import React from 'react';

const Notification = ({ message, type }) => {
    if (!message) return null; // Renders nothing if no message

    const notificationStyle = {
        position: 'fixed',
        top: '10px',
        right: '10px',
        padding: '5px 10px',
        borderRadius: '5px',
        color: '#fff',
        backgroundColor: type === 'error' ? '#f44336' : '#4CAF50',
        zIndex: 1500,
    };

    return <div style={notificationStyle}>{message}</div>;
};

export default Notification;