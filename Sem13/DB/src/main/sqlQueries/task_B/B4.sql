SELECT EXTRACT(MONTH FROM scheduled_departure) as month, COUNT(*) as cancellations FROM flights
    WHERE status = 'Cancelled' GROUP BY month ORDER BY month;