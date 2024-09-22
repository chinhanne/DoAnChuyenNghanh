import React, { Component } from 'react';

// Component hiển thị thẻ thông tin thống kê
class InfoCard extends Component {
  render() {
    const { title, value, icon } = this.props;

    return (
      <div className="col-md-3">
        <div className="info-card" style={infoCardStyle}>
          <div className="info-card-icon">
            <i className={icon} style={iconStyle}></i> {/* Icon của thẻ */}
          </div>
          <div className="info-card-content">
            <h5>{title}</h5> {/* Tiêu đề của thẻ */}
            <h2>{value}</h2> {/* Giá trị thống kê */}
          </div>
        </div>
      </div>
    );
  }
}

// Component hiển thị bảng sản phẩm
class ProductTable extends Component {
  render() {
    const { products, onDelete } = this.props;

    return (
      <table className="table table-hover" style={tableStyle}>
        <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Description</th>
            <th>Image</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{product.title}</td>
              <td>{product.description}</td>
              <td>
                <img src={product.image} alt={product.title} style={imageStyle} />
              </td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => onDelete(index)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  }
}

// Component chính cho trang Admin Dashboard
export default class AdminDashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: this.getProductData(), // Lấy dữ liệu sản phẩm từ state
    };
  }

  handleDeleteProduct = (index) => {
    const updatedProducts = [...this.state.products];
    updatedProducts.splice(index, 1); // Xóa sản phẩm tại vị trí index
    this.setState({ products: updatedProducts });
  };

  render() {
    const { products } = this.state;

    return (
      <div className="admin-dashboard" style={dashboardStyle}>
        {/* Phần thẻ thông tin thống kê */}
        <div className="row" style={marginBottomStyle}>
          <InfoCard title="Total Products" value={products.length} icon="fa fa-boxes" />
          <InfoCard title="Total Sales" value="1,250" icon="fa fa-dollar-sign" />
          <InfoCard title="New Orders" value="45" icon="fa fa-shopping-cart" />
          <InfoCard title="Customers" value="300" icon="fa fa-users" />
        </div>

        {/* Phần bảng sản phẩm */}
        <div className="product-section" style={marginBottomStyle}>
          <h3>Product Management</h3>
          <ProductTable products={products} onDelete={this.handleDeleteProduct} />
        </div>
      </div>
    );
  }

  // Hàm lấy dữ liệu sản phẩm
  getProductData() {
    return [
      { title: 'Title 1', description: 'Description 1', image: 'your-image-url-1.jpg' },
      { title: 'Title 2', description: 'Description 2', image: 'your-image-url-2.jpg' },
      { title: 'Title 3', description: 'Description 3', image: 'your-image-url-3.jpg' },
      { title: 'Title 4', description: 'Description 4', image: 'your-image-url-4.jpg' },
    ];
  }
}

// CSS-in-JS styles
const dashboardStyle = {
  padding: '20px',
};

const marginBottomStyle = {
  marginBottom: '30px',
};

const infoCardStyle = {
  display: 'flex',
  alignItems: 'center',
  backgroundColor: '#ffffff',
  padding: '20px',
  borderRadius: '8px',
  boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
  textAlign: 'center',
  marginBottom: '20px',
  transition: 'transform 0.2s, box-shadow 0.2s',
};

const iconStyle = {
  fontSize: '30px',
  marginRight: '20px',
  color: '#dc3545', // Màu đỏ của icon
};

const tableStyle = {
  width: '100%',
  backgroundColor: '#ffffff',
  borderCollapse: 'collapse',
  borderRadius: '8px',
  overflow: 'hidden',
  boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
};

const imageStyle = {
  width: '50px',
  height: '50px',
  objectFit: 'cover',
};
    