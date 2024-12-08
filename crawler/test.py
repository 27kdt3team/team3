from datetime import datetime

# Specific timestamp as a string (e.g., '2024-12-06 15:00:00')
timestamp_str = '2024-12-05 15:00:00'

# Convert the string to a datetime object
timestamp = datetime.strptime(timestamp_str, '%Y-%m-%d %H:%M:%S')

# Get the current time
current_time = datetime.now()

# Calculate the time difference
time_difference = current_time - timestamp

# Print the time difference
print(f"Time difference: {time_difference}")