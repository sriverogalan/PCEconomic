export function formatDate(date) {
  let d = new Date(date),
    month = "" + (d.getMonth() + 1),
    day = "" + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = "0" + month;
  if (day.length < 2) day = "0" + day;

  return [day, month, year].join("/");
}

export function formatCurrency(amount) {
  return (
    amount
      .toFixed(2)
      .toString()
      .replace(".", ",")
      .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €"
  );
}
