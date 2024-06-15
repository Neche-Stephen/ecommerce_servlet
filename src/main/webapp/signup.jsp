<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Signup</title>
</head>
<body>
<h2>User Signup</h2>
<form action="signup" method="post">
    <label for="signup-name">Name:</label>
    <input type="text" id="signup-name" name="fullname" required><br><br>

    <label for="signup-email">Email:</label>
    <input type="email" id="signup-email" name="email" required><br><br>

    <label for="signup-password">Password:</label>
    <input type="password" id="signup-password" name="password" required><br><br>

    <button type="submit">Sign Up</button>
</form>
</body>
</html>
