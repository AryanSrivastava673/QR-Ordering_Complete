import React, { createContext, useContext, useState } from 'react';
import Notification from '../components/Notification';

const NotificationContext = createContext();

export const useNotification = () => useContext(NotificationContext);

export const NotificationProvider = ({ children }) => {
    const [notification, setNotification] = useState({ message: '', type: '' });

    const notify = ({ message, type = 'success' }) => {
        setNotification({ message, type });
        setTimeout(() => {
            setNotification({ message: '', type: '' });  // Automatically clear notification
        }, 3000);  // Notification display duration
    };

    return (
        <NotificationContext.Provider value={{ notify }}>
            {children}
            <Notification message={notification.message} type={notification.type} />
        </NotificationContext.Provider>
    );
};