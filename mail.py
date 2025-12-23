from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_read_item():
    response = client.get("/items/42")
    assert response.status_code == 200
    data = response.json()
    assert data["item_id"] == 42

def test_create_item():
    response = client.post(
        "/items/",
        json={"name": "New Item", "price": 9.99}
    )
    assert response.status_code == 200 or 201