using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace ManaFoodProduct.Infrastructure.Migrations
{
    /// <inheritdoc />
    public partial class CreateTableProductItems : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Items_Products_ProductId",
                table: "Items");

            migrationBuilder.DropIndex(
                name: "IX_Items_ProductId",
                table: "Items");

            migrationBuilder.DropColumn(
                name: "ProductId",
                table: "Items");

            migrationBuilder.CreateTable(
                name: "product_items",
                columns: table => new
                {
                    product_id = table.Column<Guid>(type: "char(36)", nullable: false, collation: "ascii_general_ci"),
                    item_id = table.Column<Guid>(type: "char(36)", nullable: false, collation: "ascii_general_ci")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_product_items", x => new { x.product_id, x.item_id });
                    table.ForeignKey(
                        name: "FK_product_items_Items_item_id",
                        column: x => x.item_id,
                        principalTable: "Items",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_product_items_Products_product_id",
                        column: x => x.product_id,
                        principalTable: "Products",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySql:CharSet", "utf8mb4");

            migrationBuilder.CreateIndex(
                name: "IX_product_items_item_id",
                table: "product_items",
                column: "item_id");

            migrationBuilder.CreateIndex(
                name: "IX_product_items_product_id",
                table: "product_items",
                column: "product_id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "product_items");

            migrationBuilder.AddColumn<Guid>(
                name: "ProductId",
                table: "Items",
                type: "char(36)",
                nullable: true,
                collation: "ascii_general_ci");

            migrationBuilder.CreateIndex(
                name: "IX_Items_ProductId",
                table: "Items",
                column: "ProductId");

            migrationBuilder.AddForeignKey(
                name: "FK_Items_Products_ProductId",
                table: "Items",
                column: "ProductId",
                principalTable: "Products",
                principalColumn: "id");
        }
    }
}
