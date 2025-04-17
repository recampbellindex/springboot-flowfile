// liquibase formatted mongodb
// changeset amalik:orders runWith:mongosh
db = db.getSiblingDB( 'mydb' );
db.createCollection('orders');
//rollback db.orders.drop();

// changeset amalik:person2 runWith:mongosh
db = db.getSiblingDB( 'mydb' );
db.createCollection('person2');
//rollback db.person2.drop();

// changeset amalik:company runWith:mongosh
db = db.getSiblingDB( 'mydb' );
db.createCollection('company');
//rollback db.company.drop();

// changeset amalik:books runWith:mongosh
db.createCollection('books');
//rollback db.books.drop();

// changeset amalik:insertMany_products runWith:mongosh
db.products.insertMany( [
    { item: "card", qty: 15 },
    { item: "envelope", qty: 20 },
    { item: "stamps" , qty: 30 }
] );
//rollback db.products.deleteMany( {} );

// changeset amalik:insertOne_MongoDB_Tutorial runWith:mongosh
db.books.insertOne({ 
    title: 'MongoDB Tutorial',
    isbn: '0-8888-8888-8'
});
//rollback db.books.deleteOne({ title: "MongoDB Tutorial" })

// changeset amalik:insertMany_cars runWith:mongosh
db.cars.insertMany ([
    { _id: 1, name: "Alto", color: "Red",cno: "H410",speed:40,mfdcountry: "India"},
    { _id: 2, name: "Polo", color: "White",cno: "H411",speed:45,mfdcountry: "Japan" },
    { _id: 3, name: "Audi", color: "Black",cno: "H412",speed:50,mfdcountry: "Germany" }
    ]);
//rollback db.cars.deleteMany( {} );

// changeset amalik:findAndModify_car runWith:mongosh
db.car.findAndModify({
    query: { name: "Alto" },
    sort: { cno: 1 },
    update: { $inc: { speed: 100 } },
});
//rollback db.car.findAndModify({
//rollback    query: { name: "Alto" },
//rollback    sort: { cno: 1 },
//rollback    update: { $inc: { speed: -100 } },
//rollback });